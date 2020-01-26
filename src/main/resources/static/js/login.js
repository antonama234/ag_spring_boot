$(document).ready(function () {
    showTable();
});

function showTable() {
    $.ajax({
        url : "/admin/allUsers",
        contentType : "application/json",
        dataType : "json",
        success : function (data) {
            console.log(data);
        }
    });
}