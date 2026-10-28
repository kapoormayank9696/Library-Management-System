document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("bookSearch");
    const booksTable = document.getElementById("booksTable");

    if(searchInput && booksTable) {
        searchInput.addEventListener("input",() => {
            const query = searchInput.value.toLowerCase().trim();

            booksTable.querySelectorAll("tbody tr").forEach(row => {
                const matches = row.innerText.toLowerCase().includes(query);
                row.hidden =! matches;
            });
        });
    }
});


