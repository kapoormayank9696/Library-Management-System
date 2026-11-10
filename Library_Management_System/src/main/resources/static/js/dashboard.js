
let books = [];
const rows = document.getElementById("bookRows");

function render(list) {
    rows.innerHTML = list.map(b => `
    <tr>
      <td>${b.id}</td><td>${b.title}</td><td>${b.author}</td><td>${b.isbn ?? ""}</td>
      <td><button onclick="removeBook(${b.id})">Delete</button></td>
    </tr>`).join("");
}

async function load() { books = await Api.getBooks(); render(books); }

async function removeBook(id) {
    if (!confirm("Delete this book?")) return;
    await Api.deleteBook(id);
    load();
}

document.getElementById("bookForm").addEventListener("submit", async e => {
    e.preventDefault();
    await Api.addBook({
        title:  document.getElementById("title").value,
        author: document.getElementById("author").value,
        isbn:   document.getElementById("isbn").value
    });
    e.target.reset();
    load();
});

document.getElementById("search").addEventListener("input", e => {
    const q = e.target.value.toLowerCase();
    render(books.filter(b => b.title.toLowerCase().includes(q) || b.author.toLowerCase().includes(q)));
});
load();