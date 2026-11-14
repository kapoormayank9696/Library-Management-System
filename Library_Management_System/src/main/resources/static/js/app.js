const $ = id => document.getElementById(id);
let books = [], authors = [], users = [], borrows = [];

// ---------- library rules (Settings page se database me save hote hain) ----------
let DEFAULT_LOAN_DAYS = 14;   // default din (Settings se badalta hai)
let FINE_PER_DAY = 5;         // due date ke baad ₹ per day (Settings se badalta hai)

// ---------- helpers ----------
const esc = s => String(s ?? "").replace(/[&<>"']/g,
    c => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]));
const money = p => `₹${Number(p ?? 0).toFixed(2)}`;
const fmtDate = d => {
    if (!d) return "—";
    const x = d instanceof Date ? d : new Date(d + "T00:00:00");
    return isNaN(x) ? esc(d) : x.toLocaleDateString("en-IN", { day: "2-digit", month: "short", year: "numeric" });
};
const todayISO = () => {                       // local date, UTC nahi
    const d = new Date();
    d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
    return d.toISOString().slice(0, 10);
};
const match = (q, ...vals) => vals.some(v => String(v ?? "").toLowerCase().includes(q));
const query = id => ($(id)?.value || "").trim().toLowerCase();
const empty = (cols, text) =>
    `<tr><td colspan="${cols}" class="empty"><i class="bi bi-inbox"></i><p>${text}</p></td></tr>`;

const authorIdOf = b => b.author?.authorId ?? b.authorId;
const authorOf = b => b.author?.authorName ?? authors.find(a => a.authorId === authorIdOf(b))?.authorName ?? "";
const borrowUserId = r => r.user?.userId ?? r.userId;
const borrowBookId = r => r.book?.bookId ?? r.bookId;
const userOf = r => r.user?.username ?? users.find(u => u.userId === borrowUserId(r))?.username ?? "";
const bookOf = r => r.book?.title ?? books.find(b => b.bookId === borrowBookId(r))?.title ?? "";

// ---------- due date / overdue / status ----------
const parseD = d => new Date(d + "T00:00:00");

// Us record ki apni due date. Purane records (due date nahi) ke liye default din lagte hain.
const dueDateOf = r => {
    if (r.dueDate) return parseD(r.dueDate);
    const d = parseD(r.borrowDate);
    d.setDate(d.getDate() + DEFAULT_LOAN_DAYS);
    return d;
};
function overdueDays(r) {
    const end = r.returnDate ? parseD(r.returnDate) : parseD(todayISO());
    return Math.max(0, Math.round((end - dueDateOf(r)) / 86400000));
}
const borrowState = r => r.returnDate ? "returned" : (overdueDays(r) > 0 ? "overdue" : "active");

function statusBadge(r) {
    const s = borrowState(r), late = overdueDays(r);
    if (s === "returned") return `<span class="badge green">Returned</span>` + (late > 0 ? ` <span class="badge amber">Late</span>` : "");
    if (s === "overdue")  return `<span class="badge red">Overdue · ${late}d</span>`;
    return `<span class="badge blue">Borrowed</span>`;
}
function stockBadge(q) {
    if (q <= 0) return `<span class="badge red">Out of stock</span>`;
    if (q <= 2) return `<span class="badge amber">${q} left</span>`;
    return `<span class="badge green">${q} in stock</span>`;
}

// edit + delete icons. "lock" me reason do to delete disabled ho jata hai
const actionBtns = (kind, id, lock = "") => `
    <button class="icon-btn edit" data-action="edit${kind}" data-id="${id}" title="Edit"><i class="bi bi-pencil-square"></i></button>
    ${lock
    ? `<button class="icon-btn" disabled title="${esc(lock)}"><i class="bi bi-trash3"></i></button>`
    : `<button class="icon-btn del" data-action="del${kind}" data-id="${id}" title="Delete"><i class="bi bi-trash3"></i></button>`}`;

// ---------- toast ----------
function toast(msg, type = "success") {
    const t = document.createElement("div");
    t.className = `toast ${type}`;
    t.innerHTML = `<i class="bi ${type === "success" ? "bi-check-circle-fill" : "bi-exclamation-circle-fill"}"></i><span>${esc(msg)}</span>`;
    $("toasts").appendChild(t);
    setTimeout(() => t.classList.add("hide"), 3500);
    setTimeout(() => t.remove(), 3900);
}

// ---------- modal form ----------
let currentSubmit = null;
const show = id => $(id).classList.remove("hidden");
const hide = id => $(id).classList.add("hidden");

function openForm({ title, fields, values = {}, submitText = "Save", info = "", onSubmit }) {
    $("modalTitle").textContent = title;
    $("modalFields").innerHTML = (info ? `<div class="full">${info}</div>` : "") + fields.map(f => {
        const v = values[f.name] ?? f.default ?? "";
        let input;
        if (f.type === "select") {
            input = `<select name="${f.name}" ${f.required ? "required" : ""}>
                <option value="">Select...</option>` +
                f.options.map(o => `<option value="${esc(o.value)}" ${String(o.value) === String(v) ? "selected" : ""}>${esc(o.label)}</option>`).join("") +
                `</select>`;
        } else {
            input = `<input name="${f.name}" type="${f.type || "text"}" value="${esc(v)}"
                ${f.required ? "required" : ""} ${f.attrs || ""} placeholder="${esc(f.placeholder || "")}">`;
        }
        return `<label class="field ${f.full ? "full" : ""}"><span>${esc(f.label)}</span>${input}</label>`;
    }).join("");
    $("modalSave").textContent = submitText;
    currentSubmit = onSubmit;
    show("modal");
    $("modalFields").querySelector("input,select")?.focus();
}

$("modalForm").onsubmit = async e => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(e.target));
    const btn = $("modalSave");
    btn.disabled = true;
    try { await currentSubmit(data); hide("modal"); }
    catch (err) { toast(err.message, "error"); }
    finally { btn.disabled = false; }
};
$("modalClose").onclick = $("modalCancel").onclick = () => hide("modal");
$("modal").addEventListener("mousedown", e => { if (e.target.id === "modal") hide("modal"); });
document.addEventListener("keydown", e => { if (e.key === "Escape") { hide("modal"); hide("confirmBox"); } });

// ---------- confirm dialog ----------
function confirmDialog(title, text, okText = "Delete") {
    return new Promise(resolve => {
        $("confirmTitle").textContent = title;
        $("confirmText").textContent = text;
        $("confirmOk").textContent = okText;
        show("confirmBox");
        const done = v => { hide("confirmBox"); $("confirmOk").onclick = $("confirmCancel").onclick = null; resolve(v); };
        $("confirmOk").onclick = () => done(true);
        $("confirmCancel").onclick = () => done(false);
    });
}

async function remove(kind, id, label, name) {
    const ok = await confirmDialog(`Delete ${label}?`,
        `"${name}" will be permanently removed. This cannot be undone.`);
    if (!ok) return;
    try {
        await Api[kind].remove(id);
        toast(`${label[0].toUpperCase() + label.slice(1)} deleted`);
        await loadAll();
    } catch (e) { toast(e.message, "error"); }
}

// ---------- add / edit forms ----------
const bookPayload = d => {
    const authorId = parseInt(d.authorId, 10);
    return {
        bookName: d.bookName.trim(),
        title:    d.title.trim(),
        price:    parseFloat(d.price),
        quantity: parseInt(d.quantity, 10),
        authorId,
        author: { authorId }          // dono shape bhejte hain; jo entity use nahi karti wo ignore ho jati hai
    };
};

function openBookForm(book) {
    const editing = !!book;
    openForm({
        title: editing ? "Edit Book" : "Add Book",
        fields: [
            { name: "bookName", label: "Book name", required: true, attrs: 'maxlength="50"' },
            { name: "title", label: "Title", required: true, attrs: 'maxlength="100"' },
            { name: "authorId", label: "Author", type: "select", required: true, full: true,
                options: authors.map(a => ({ value: a.authorId, label: a.authorName })) },
            { name: "price", label: "Price (₹)", type: "number", required: true, attrs: 'min="0" step="0.01"' },
            { name: "quantity", label: "Quantity", type: "number", required: true, default: 1, attrs: 'min="0" step="1"' },
        ],
        values: editing ? { ...book, authorId: authorIdOf(book) } : {},
        submitText: editing ? "Update Book" : "Add Book",
        onSubmit: async d => {
            const p = bookPayload(d);
            editing ? await Api.books.update(book.bookId, p) : await Api.books.add(p);
            toast(editing ? "Book updated" : "Book added");
            await loadAll();
        }
    });
}

function openAuthorForm(author) {
    const editing = !!author;
    openForm({
        title: editing ? "Edit Author" : "Add Author",
        fields: [{ name: "authorName", label: "Author name", required: true, full: true, attrs: 'maxlength="50"' }],
        values: author || {},
        submitText: editing ? "Update Author" : "Add Author",
        onSubmit: async d => {
            const p = { authorName: d.authorName.trim() };
            editing ? await Api.authors.update(author.authorId, p) : await Api.authors.add(p);
            toast(editing ? "Author updated" : "Author added");
            await loadAll();
        }
    });
}

function openUserForm(user) {
    const editing = !!user;
    openForm({
        title: editing ? "Edit User" : "Add User",
        fields: [
            { name: "username", label: "Username", required: true, attrs: 'maxlength="50"' },
            { name: "email", label: "Email", type: "email", required: true, attrs: 'maxlength="50"' },
        ],
        values: user || {},
        submitText: editing ? "Update User" : "Add User",
        onSubmit: async d => {
            const p = { username: d.username.trim(), email: d.email.trim() };
            editing ? await Api.users.update(user.userId, p) : await Api.users.add(p);
            toast(editing ? "User updated" : "User added");
            await loadAll();
        }
    });
}

// Issue Book: loan period Settings ki default value se bharta hai, issue ke waqt badal bhi sakte ho
function openBorrowForm() {
    openForm({
        title: "Issue Book",
        fields: [
            { name: "userId", label: "Member", type: "select", required: true, full: true,
                options: users.map(u => ({ value: u.userId, label: u.username })) },
            { name: "bookId", label: "Book", type: "select", required: true, full: true,
                options: books.filter(b => b.quantity > 0)
                    .map(b => ({ value: b.bookId, label: `${b.title} (${b.quantity} left)` })) },
            { name: "borrowDate", label: "Borrow date", type: "date", required: true, default: todayISO() },
            { name: "loanDays", label: "Loan period (days)", type: "number", required: true,
                default: DEFAULT_LOAN_DAYS, attrs: 'min="1" max="365" step="1"' },
        ],
        submitText: "Issue Book",
        onSubmit: async d => {
            const userId = parseInt(d.userId, 10), bookId = parseInt(d.bookId, 10);
            const loanDays = parseInt(d.loanDays, 10);
            await Api.borrows.add({ userId, bookId, borrowDate: d.borrowDate, loanDays,
                user: { userId }, book: { bookId } });
            toast("Book issued");
            await loadAll();
        }
    });

    // quick buttons + live due-date preview
    const box = $("modalFields");
    box.insertAdjacentHTML("beforeend", `
        <div class="full quick"><span>Quick select:</span>
            ${[7, 14, 21, 30].map(n => `<button type="button" class="chip" data-days="${n}">${n} days</button>`).join("")}
        </div>
        <div class="full due-preview" id="duePreview"></div>`);

    const dateEl = box.querySelector('[name="borrowDate"]');
    const daysEl = box.querySelector('[name="loanDays"]');
    const update = () => {
        const n = parseInt(daysEl.value, 10);
        if (!dateEl.value || !n || n < 1) { $("duePreview").innerHTML = ""; return; }
        const d = parseD(dateEl.value);
        d.setDate(d.getDate() + n);
        $("duePreview").innerHTML = `<i class="bi bi-calendar-check"></i> Due date: <strong>${fmtDate(d)}</strong>`;
    };
    dateEl.oninput = daysEl.oninput = update;
    box.querySelectorAll("[data-days]").forEach(b => b.onclick = () => { daysEl.value = b.dataset.days; update(); });
    update();
}

// Alag "Return Book" screen
function openReturnForm(r) {
    if (!r) return;
    const late = overdueDays(r);
    openForm({
        title: "Return Book",
        info: `<div class="info-box">
            <div><span>Member</span><strong>${esc(userOf(r))}</strong></div>
            <div><span>Book</span><strong>${esc(bookOf(r))}</strong></div>
            <div><span>Borrowed on</span><strong>${fmtDate(r.borrowDate)}</strong></div>
            <div><span>Due date</span><strong>${fmtDate(dueDateOf(r))}</strong></div>
            ${late > 0
            ? `<div class="warn"><i class="bi bi-exclamation-triangle"></i> Overdue by ${late} day(s). Fine: ${money(late * FINE_PER_DAY)} (as of today)</div>`
            : `<div class="okline"><i class="bi bi-check-circle"></i> Within the due date, no fine</div>`}
        </div>`,
        fields: [{ name: "returnDate", label: "Return date", type: "date", required: true,
            default: todayISO(), full: true, attrs: `min="${r.borrowDate}"` }],
        submitText: "Confirm Return",
        onSubmit: async d => {
            await Api.borrows.giveBack(r.borrowId, d.returnDate);
            toast("Book returned. Record moved to Returned Books");
            await loadAll();
        }
    });
}

// ---------- row actions ----------
const actions = {
    editBook:   id => openBookForm(books.find(x => x.bookId === id)),
    delBook:    id => remove("books", id, "book", books.find(x => x.bookId === id)?.title),
    editAuthor: id => openAuthorForm(authors.find(x => x.authorId === id)),
    delAuthor:  id => remove("authors", id, "author", authors.find(x => x.authorId === id)?.authorName),
    editUser:   id => openUserForm(users.find(x => x.userId === id)),
    delUser:    id => remove("users", id, "user", users.find(x => x.userId === id)?.username),
    returnBorrow: id => openReturnForm(borrows.find(x => x.borrowId === id)),
};
document.addEventListener("click", e => {
    const b = e.target.closest("[data-action]");
    if (b) actions[b.dataset.action]?.(Number(b.dataset.id));
});

// ---------- render ----------
function setCount(id, shown, total) { if ($(id)) $(id).textContent = `${shown} of ${total}`; }

function renderBooks() {
    const s = query("searchBooks");
    const list = books.filter(b => match(s, b.bookName, b.title, authorOf(b)));
    setCount("countBooks", list.length, books.length);
    $("bookRows").innerHTML = list.length ? list.map(b => {
        const lock = borrows.some(r => borrowBookId(r) === b.bookId) ? "Has borrow history, cannot be deleted" : "";
        return `<tr>
        <td>${b.bookId}</td><td><strong>${esc(b.bookName)}</strong></td><td>${esc(b.title)}</td>
        <td>${esc(authorOf(b))}</td><td>${money(b.price)}</td><td>${stockBadge(b.quantity)}</td>
        <td class="right">${actionBtns("Book", b.bookId, lock)}</td></tr>`;
    }).join("") : empty(7, "No books found");
}

function renderAuthors() {
    const s = query("searchAuthors");
    const list = authors.filter(a => match(s, a.authorName));
    setCount("countAuthors", list.length, authors.length);
    $("authorRows").innerHTML = list.length ? list.map(a => {
        const lock = books.some(b => authorIdOf(b) === a.authorId) ? "Author still has books, cannot be deleted" : "";
        return `<tr>
        <td>${a.authorId}</td><td><strong>${esc(a.authorName)}</strong></td>
        <td class="right">${actionBtns("Author", a.authorId, lock)}</td></tr>`;
    }).join("") : empty(3, "No authors found");
}

function renderUsers() {
    const s = query("searchUsers");
    const list = users.filter(u => match(s, u.username, u.email));
    setCount("countUsers", list.length, users.length);
    $("userRows").innerHTML = list.length ? list.map(u => {
        const lock = borrows.some(r => borrowUserId(r) === u.userId) ? "Has borrow history, cannot be deleted" : "";
        return `<tr>
        <td>${u.userId}</td><td><strong>${esc(u.username)}</strong></td><td>${esc(u.email)}</td>
        <td class="right">${actionBtns("User", u.userId, lock)}</td></tr>`;
    }).join("") : empty(4, "No users found");
}

// Borrow / Return page: sirf wahi books jo abhi bahar hain
let borrowFilter = "all";
function renderBorrows() {
    const s = query("searchBorrows");
    const issued = borrows.filter(r => !r.returnDate);
    const list = issued
        .filter(r => borrowFilter === "all" || borrowState(r) === borrowFilter)
        .filter(r => match(s, userOf(r), bookOf(r)))
        .sort((a, b) => b.borrowId - a.borrowId);
    setCount("countBorrows", list.length, issued.length);
    $("borrowRows").innerHTML = list.length ? list.map(r => `<tr>
        <td>${r.borrowId}</td><td>${esc(userOf(r))}</td><td>${esc(bookOf(r))}</td>
        <td>${fmtDate(r.borrowDate)}</td><td>${fmtDate(dueDateOf(r))}</td>
        <td>${statusBadge(r)}</td>
        <td class="right"><button class="btn return" data-action="returnBorrow" data-id="${r.borrowId}"><i class="bi bi-box-arrow-in-down"></i>Return Book</button></td>
        </tr>`).join("") : empty(7, "No books are currently issued");
}

// Returned Books page: poori history
let returnFilter = "all";
function renderReturns() {
    if (!$("returnRows")) return;
    const s = query("searchReturns");
    const done = borrows.filter(r => r.returnDate);
    const list = done
        .filter(r => {
            const late = overdueDays(r) > 0;
            return returnFilter === "all" || (returnFilter === "late" ? late : !late);
        })
        .filter(r => match(s, userOf(r), bookOf(r)))
        .sort((a, b) => parseD(b.returnDate) - parseD(a.returnDate) || b.borrowId - a.borrowId);
    setCount("countReturns", list.length, done.length);
    $("returnRows").innerHTML = list.length ? list.map(r => {
        const late = overdueDays(r);
        return `<tr>
        <td>${r.borrowId}</td><td>${esc(userOf(r))}</td><td>${esc(bookOf(r))}</td>
        <td>${fmtDate(r.borrowDate)}</td><td>${fmtDate(dueDateOf(r))}</td><td>${fmtDate(r.returnDate)}</td>
        <td>${late > 0 ? `<span class="badge red">Late · ${late}d</span>` : `<span class="badge green">On time</span>`}</td>
        <td>${late > 0 ? `<strong>${money(late * FINE_PER_DAY)}</strong>` : "—"}</td></tr>`;
    }).join("") : empty(8, "No returned books yet");
}

function renderDashboard() {
    $("statBooks").textContent   = books.length;
    $("statAuthors").textContent = authors.length;
    $("statUsers").textContent   = users.length;
    $("statActive").textContent  = borrows.filter(r => !r.returnDate).length;

    const recent = [...borrows].sort((a, b) => b.borrowId - a.borrowId).slice(0, 5);
    $("recentRows").innerHTML = recent.length ? recent.map(r => `<tr>
        <td>${esc(userOf(r))}</td><td>${esc(bookOf(r))}</td><td>${fmtDate(r.borrowDate)}</td><td>${statusBadge(r)}</td></tr>`).join("")
        : empty(4, "No borrow records yet");

    const low = books.filter(b => b.quantity <= 2).sort((a, b) => a.quantity - b.quantity).slice(0, 6);
    $("lowStock").innerHTML = low.length
        ? low.map(b => `<li><span>${esc(b.title)}</span>${stockBadge(b.quantity)}</li>`).join("")
        : `<li class="muted">All books are well stocked</li>`;
}

function renderAll() {
    renderDashboard(); renderBooks(); renderAuthors(); renderUsers(); renderBorrows(); renderReturns();
}

async function loadAll() {
    const names = ["authors", "books", "users", "borrows"];
    const results = await Promise.allSettled(
        [Api.authors.all(), Api.books.all(), Api.users.all(), Api.borrows.all()]);
    const failed = [];
    const pick = (i, old) => results[i].status === "fulfilled" ? results[i].value : (failed.push(names[i]), old);

    authors = pick(0, authors); books = pick(1, books);
    users   = pick(2, users);   borrows = pick(3, borrows);

    renderAll();
    if (failed.length) toast("Could not load: " + failed.join(", "), "error");
}

// ---------- settings ----------
async function loadSettings() {
    try {
        const s = await Api.settings.get();
        if (Number(s.loanDays) >= 1) DEFAULT_LOAN_DAYS = Number(s.loanDays);
        if (!isNaN(Number(s.finePerDay))) FINE_PER_DAY = Number(s.finePerDay);
    } catch (e) { /* server na mile to defaults rehne do */ }
    if ($("setLoanDays")) $("setLoanDays").value = DEFAULT_LOAN_DAYS;
    if ($("setFine"))     $("setFine").value = FINE_PER_DAY;
}

if ($("settingsForm")) {
    $("settingsForm").onsubmit = async e => {
        e.preventDefault();
        const btn = $("settingsSave");
        btn.disabled = true;
        try {
            await Api.settings.save({
                loanDays:   parseInt($("setLoanDays").value, 10),
                finePerDay: parseFloat($("setFine").value)
            });
            await loadSettings();
            renderAll();
            toast(`Saved. New books will be issued for ${DEFAULT_LOAN_DAYS} days`);
        } catch (err) { toast(err.message, "error"); }
        finally { btn.disabled = false; }
    };
}

// ---------- navigation ----------
const views = {
    dashboard: { title: "Dashboard",       add: null },
    books:     { title: "Books",           add: ["Add Book",   () => openBookForm()] },
    authors:   { title: "Authors",         add: ["Add Author", () => openAuthorForm()] },
    users:     { title: "Users",           add: ["Add User",   () => openUserForm()] },
    borrows:   { title: "Borrow / Return", add: ["Issue Book", () => openBorrowForm()] },
    returns:   { title: "Returned Books",  add: null },
    settings:  { title: "Settings",        add: null },
};
let currentView = "dashboard";

function go(view) {
    if (!views[view] || !$("view-" + view)) view = "dashboard";
    currentView = view;
    document.querySelectorAll(".view").forEach(v => v.classList.add("hidden"));
    $("view-" + view).classList.remove("hidden");
    document.querySelectorAll(".nav-item").forEach(n => n.classList.toggle("active", n.dataset.view === view));
    $("pageTitle").textContent = views[view].title;
    const add = views[view].add;
    $("addBtn").classList.toggle("hidden", !add);
    if (add) $("addLabel").textContent = add[0];
    $("sidebar").classList.remove("open");
}

document.querySelectorAll(".nav-item").forEach(n => n.onclick = () => { location.hash = n.dataset.view; });
window.addEventListener("hashchange", () => go(location.hash.slice(1)));
$("addBtn").onclick = () => views[currentView].add?.[1]();
$("menuBtn").onclick = () => $("sidebar").classList.toggle("open");

["searchBooks", "searchAuthors", "searchUsers", "searchBorrows", "searchReturns"].forEach(id =>
    $(id)?.addEventListener("input", () => {
        renderBooks(); renderAuthors(); renderUsers(); renderBorrows(); renderReturns();
    }));

function bindChips(containerId, onPick) {
    $(containerId)?.addEventListener("click", e => {
        const c = e.target.closest(".chip");
        if (!c) return;
        document.querySelectorAll(`#${containerId} .chip`).forEach(x => x.classList.toggle("active", x === c));
        onPick(c.dataset.filter);
    });
}
bindChips("borrowChips", f => { borrowFilter = f; renderBorrows(); });
bindChips("returnChips", f => { returnFilter = f; renderReturns(); });

// ---------- start ----------
go(location.hash.slice(1));
loadSettings().then(loadAll);