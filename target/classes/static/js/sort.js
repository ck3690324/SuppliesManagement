"use strict";
// 状態管理
let currentSortColumn = -1;
let isAsc = true;
let currentFilterText = "";
let currentFilterColumn = -1;

// HTMLの読み込みが完了した時点でイベントやスタイルをセット
document.addEventListener("DOMContentLoaded", () => {
    const table = document.querySelector("#suppliesTable");
    if (!table) return;

    const tbody = table.tBodies[0];
    const ths = table.querySelectorAll("thead th");

    // カーソル当てるときの色変化
    ths.forEach((th, idx) => {
        //if (idx < 4) {
            th.style.cursor = "pointer";
            th.style.userSelect = "none";
            // ホバー時の背景色
            th.addEventListener("mouseover", () => th.style.backgroundColor = "#f1f1f1");
            th.addEventListener("mouseout", () => th.style.backgroundColor = "");
        //}
    });

    // セル(TD)のフィルター処理
    tbody.addEventListener("click", function(e) {
        const clickedCell = e.target;
        
        // 操作欄以外は処理する
        if (clickedCell.tagName === "TD" && clickedCell.cellIndex < 4) {
            const text = clickedCell.textContent.trim();
            const colIndex = clickedCell.cellIndex;

            if (currentFilterText === text && currentFilterColumn === colIndex) {
                clearFilter();
            }
			else {
                filterTable(text, colIndex);
            }
        }
    });
});

// ソート
function sortTable(columnIndex) {
    const tbody = document.querySelector("#suppliesTable tBody");
    if (!tbody) return;
    
    const rows = Array.from(tbody.rows);

    if (currentSortColumn === columnIndex) {
        isAsc = !isAsc;
    }
	else {
        isAsc = true;
        currentSortColumn = columnIndex;
    }

    rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[columnIndex].textContent.trim();
        const cellB = rowB.cells[columnIndex].textContent.trim();

        return isAsc ? cellA.localeCompare(cellB, undefined, {numeric: true}) : cellB.localeCompare(cellA, undefined, {numeric: true});
    });

    rows.forEach(row => tbody.appendChild(row));
}

// フィルター
function filterTable(text, columnIndex) {
    currentFilterText = text;
    currentFilterColumn = columnIndex;
    
    const rows = document.querySelectorAll("#suppliesTable tbody tr");

    rows.forEach(row => {
        const cellText = row.cells[columnIndex].textContent.trim();
        
        if (cellText === text) {
            row.style.display = "";
            row.cells[columnIndex].style.backgroundColor = "#f1f1f1";
        }
		else {
            row.style.display = "none";
            row.cells[columnIndex].style.backgroundColor = "";
        }
        
        for(let i = 0; i < row.cells.length; i++) {
            if(i !== columnIndex) {
				row.cells[i].style.backgroundColor = "";
			}
        }
    });
}

// フィルター解除
function clearFilter() {
    currentFilterText = "";
    currentFilterColumn = -1;
	
    const rows = document.querySelectorAll("#suppliesTable tbody tr");
    rows.forEach(row => {
        row.style.display = ""; 
        for(let cell of row.cells) {
            cell.style.backgroundColor = "";
        }
    });
}