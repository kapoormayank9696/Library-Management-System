// If your update endpoints are PUT /api/users/update/{id}, keep "/update".
// If they are PUT /api/users/{id}, change this to "".
const UPDATE_PATH = "/update";

async function readError(res, method) {
    let text = await res.text(), msg = text;
    try { const j = JSON.parse(text); msg = j.message || j.error || text; } catch (e) {}

    if (res.status === 405)
        return `${method} is not supported on this URL. Check the mapping in your controller (it needs /{id}).`;
    if (res.status === 404 && (!msg || msg.length > 150))
        return "Not found. Check the URL in js/api.js against your controller.";
    return msg && msg.length < 200 ? msg : `Request failed (${res.status}).`;
}

async function apiFetch(path, options = {}) {
    let res;
    try {
        res = await fetch(path, {
            ...options,
            headers: { "Content-Type": "application/json", ...(options.headers || {}) }
        });
    } catch (e) {
        throw new Error("Cannot reach the server. Is the application running?");
    }
    if (!res.ok) throw new Error(await readError(res, options.method || "GET"));

    const text = await res.text();
    if (!text) return null;
    try { return JSON.parse(text); } catch (e) { return text; }
}

const resource = base => ({
    all:    ()      => apiFetch(base),
    add:    d       => apiFetch(base, { method: "POST", body: JSON.stringify(d) }),
    update: (id, d) => apiFetch(`${base}${UPDATE_PATH}/${id}`, { method: "PUT", body: JSON.stringify(d) }),
    remove: id      => apiFetch(`${base}/${id}`, { method: "DELETE" }),
});

const Api = {
    books:   resource("/api/books"),
    authors: resource("/api/authors"),
    users:   resource("/api/users"),
    borrows: {
        ...resource("/api/borrows"),
        giveBack: id => apiFetch(`/api/borrows/${id}/return`, { method: "PUT" }),
    },
};