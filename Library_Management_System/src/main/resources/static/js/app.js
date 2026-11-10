const $ = id => document.getElementById(id);
let books = [], authors = [], users = [], borrows = [];

const esc = s => String(s ?? "").replace(/[&<>"']/g,
    c => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]));

function showMsg(text, ok = true) {
    const m = $("msg");
    m.textContent = text;
    m.className = "msg " + (ok ? "ok" : "bad");
    setTimeout(() => (m.textContent = ""), 4000);
}

async function run(fn, success) {
    try { await fn(); if (success) showMsg(success); await loadAll(); }
    catch (e) { showMsg(e.message, false); }
}

// ---------- tabs ----------
document.querySelectorAll(".tab").forEach(btn => btn.onclick = () => {
    document.querySelectorAll(".tab").forEach(b => b.classList.remove("active"));
    document.querySelectorAll(".panel").forEach(p => p.classList.add("hidden"));
    btn.classList.add("active");
    $(btn.dataset.tab).classList.remove("hidden");
});

// ---------- lookups ----------
const authorOf = b => b.author?.authorName ?? authors.find(a => a.authorId === b.authorId)?.authorName ?? "";
const userOf   = r => r.user?.username ?? users.find(u => u.userId === r.userId)?.username ?? "";
const bookOf   = r => r.book?.title ?? books.find(b => b.bookId === r.bookId)?.title ?? "";

// ---------- render ----------
function renderBooks(list = books) {
    $("bookRows").innerHTML = list.map(b => `
    <tr><td>${b.bookId}</td><td>${esc(b.bookName)}</td><td>${esc(b.title)}</td>
    <td>${esc(authorOf(b))}</td><td>₹${esc(b.price)}</td><td>${b.quantity}</td>
    <td><button class="danger" onclick="delBook(${b.bookId})">Delete</button></td></tr>`).join("");
}
function renderAuthors() {
    $("authorRows").innerHTML = authors.map(a => `
    <tr><td>${a.authorId}</td><td>${esc(a.authorName)}</td>
    <td><button class="danger" onclick="delAuthor(${a.authorId})">Delete</button></td></tr>`).join("");
    $("bookAuthor").innerHTML = `<option value="">Select author</option>` +
        authors.map(a => `<option value="${a.authorId}">${esc(a.authorName)}</option>`).join("");
}
function renderUsers() {
    $("userRows").innerHTML = users.map(u => `
    <tr><td>${u.userId}</td><td>${esc(u.username)}</td><td>${esc(u.email)}</td>
    <td><button class="danger" onclick="delUser(${u.userId})">Delete</button></td></tr>`).join("");
    $("borrowUser").innerHTML = `<option value="">Select user</option>` +
        users.map(u => `<option value="${u.userId}">${esc(u.username)}</option>`).join("");
}
function renderBorrows() {
    $("borrowBook").innerHTML = `<option value="">Select book</option>` +
        books.filter(b => b.quantity > 0)
            .map(b => `<option value="${b.bookId}">${esc(b.title)} (${b.quantity} left)</option>`).join("");
    $("borrowRows").innerHTML = borrows.map(r => `
    <tr><td>${r.borrowId}</td><td>${esc(userOf(r))}</td><td>${esc(bookOf(r))}</td>
    <td>${esc(r.borrowDate)}</td><td>${r.returnDate ? esc(r.returnDate) : "<em>Not returned</em>"}</td>
    <td>${r.returnDate ? "" : `<button onclick="doReturn(${r.borrowId})">Return</button>`}</td></tr>`).join("");
}

async function loadAll() {
    [authors, books, users, borrows] =
        await Promise.all([Api.getAuthors(), Api.getBooks(), Api.getUsers(), Api.getBorrows()]);
    renderAuthors(); renderBooks(); renderUsers(); renderBorrows();
}

// ---------- actions ----------
window.delBook   = id => confirm("Delete this book?")   && run(() => Api.deleteBook(id),   "Book deleted");
window.delAuthor = id => confirm("Delete this author?") && run(() => Api.deleteAuthor(id), "Author deleted");
window.delUser   = id => confirm("Delete this user?")   && run(() => Api.deleteUser(id),   "User deleted");
window.doReturn  = id => run(() => Api.returnBook(id), "Book returned");

$("authorForm").onsubmit = e => { e.preventDefault();
    run(() => Api.addAuthor({ authorName: $("authorName").value.trim() }), "Author added")
        .then(() => e.target.reset()); };

$("userForm").onsubmit = e => { e.preventDefault();
    run(() => Api.addUser({ username: $("userName").value.trim(), email: $("userEmail").value.trim() }), "User added")
        .then(() => e.target.reset()); };

$("bookForm").onsubmit = e => { e.preventDefault();
    run(() => Api.addBook({
        bookName: $("bookName").value.trim(),
        title:    $("bookTitle").value.trim(),
        price:    parseFloat($("bookPrice").value),
        quantity: parseInt($("bookQty").value, 10),
        authorId: parseInt($("bookAuthor").value, 10)
    }), "Book added").then(() => e.target.reset()); };

$("borrowForm").onsubmit = e => { e.preventDefault();
    run(() => Api.borrowBook({
        userId: parseInt($("borrowUser").value, 10),
        bookId: parseInt($("borrowBook").value, 10),
        borrowDate: $("borrowDate").value
    }), "Book borrowed").then(() => e.target.reset()); };

$("bookSearch").oninput = e => {
    const q = e.target.value.toLowerCase();
    renderBooks(books.filter(b =>
        [b.bookName, b.title, authorOf(b)].some(v => String(v ?? "").toLowerCase().includes(q))));
};

$("borrowDate").valueAsDate = new Date();
loadAll().catch(e => showMsg("Could not load data: " + e.message, false));