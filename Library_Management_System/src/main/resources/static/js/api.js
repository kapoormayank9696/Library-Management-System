async function apiFetch(path, options = {}) {
    const res = await fetch(path, {
        ...options,
        headers: { "Content-Type": "application/json", ...(options.headers || {}) }
    });
    if (!res.ok) {
        let text = await res.text();
        try { text = JSON.parse(text).message || text; } catch (e) {}
        throw new Error(text || res.statusText);
    }
    const text = await res.text();
    return text ? JSON.parse(text) : null;
}

const Api = {
    // Books
    getBooks:    ()     => apiFetch("/api/books"),
    addBook:     (b)    => apiFetch("/api/books", { method: "POST", body: JSON.stringify(b) }),
    deleteBook:  (id)   => apiFetch(`/api/books/${id}`, { method: "DELETE" }),

    // Authors
    getAuthors:  ()     => apiFetch("/api/authors"),
    addAuthor:   (a)    => apiFetch("/api/authors", { method: "POST", body: JSON.stringify(a) }),
    deleteAuthor:(id)   => apiFetch(`/api/authors/${id}`, { method: "DELETE" }),

    // Users
    getUsers:    ()     => apiFetch("/api/users"),
    addUser:     (u)    => apiFetch("/api/users", { method: "POST", body: JSON.stringify(u) }),
    deleteUser:  (id)   => apiFetch(`/api/users/${id}`, { method: "DELETE" }),

    // Borrow
    getBorrows:  ()     => apiFetch("/api/borrows"),
    borrowBook:  (b)    => apiFetch("/api/borrows", { method: "POST", body: JSON.stringify(b) }),
    returnBook:  (id)   => apiFetch(`/api/borrows/${id}/return`, { method: "PUT" }),
};