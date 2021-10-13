$(document).ready(function() {

    $('.ui.dropdown')
        .dropdown()
    ;

    const infoToast = $('#infoToast');
    const successToast = $('#successToast');
    const warningToast = $('#warningToast');
    const errorToast = $('#errorToast');

    if(infoToast.length) {
        $.uiAlert({
            textHead: escapeHtml(infoToast.val()),
            text: '',
            bgcolor: '#3399ff',
            textcolor: '#fff',
            position: 'bottom-right', // top And bottom ||  left / center / right
            icon: 'checkmark box',
            time: 5
        });
    }

    if(successToast.length) {
        $.uiAlert({
            textHead: escapeHtml(successToast.val()),
            text: '',
            bgcolor: '#21ba45',
            textcolor: '#fff',
            position: 'bottom-right', // top And bottom ||  left / center / right
            icon: 'checkmark box',
            time: 5
        });
    }

    if(errorToast.length) {
        $.uiAlert({
            textHead: escapeHtml(errorToast.val()),
            text: '',
            bgcolor: '#ff3333',
            textcolor: '#fff',
            position: 'bottom-right', // top And bottom ||  left / center / right
            icon: 'checkmark box',
            time: 5
        });
    }

    function escapeHtml(unsafe) {
        return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }
});
