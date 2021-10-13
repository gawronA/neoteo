$(document).ready(function() {

    $('button.subscription').click(function(event) {
        const id = $(event.target).data('id');

        $.get(`account/changeSubscription?id=${id}`, function (data) {
            const loginForm = $(data).find('form[name="loginForm"]');
            if(loginForm) {
                window.location.href = 'login';
            }
            else {
                $('#confirmSubscriptionChangeModal .content').html(data);
                $('#confirmSubscriptionChangeModal').modal('setting', {
                    closable: false,
                    onApprove: function (click) {
                        console.log("hurrayy");
                    }
                }).modal('show');
            }
        });
    });
});
