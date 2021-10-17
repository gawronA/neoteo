$(document).ready(function() {
    $('.ui.sticky')
        .sticky({
            context: '.full-height'
        })
    ;

    $('button.properties').click(function(event) {
        const header = $('#propertiesModal .header');
        const id = $(event.target).data('id');
        const name = $(event.target).data('name');

        if(id === 0) {
            header.html(`${header.data('add')}`);
            $('button#delete').css('display', 'none');
        }
        else {
            header.html(`${header.data('edit')} ${name}`);
            $('button#delete').css('display', 'inline-block');
        }

        $.get(`properties/${id}`, function (data) {
            console.log(`GOT property ${id} modal form`);
            $('#propertiesModal .content').html(data);
            $('#propertiesModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#propertiesModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button.payments').click(function(event) {
        const header = $('#paymentModal .header');
        const id = $(event.target).data('id');
        const name = $(event.target).data('name');

        if(id === 0) {
            header.html(`${header.data('add')}`);
            $('button#delete').css('display', 'none');
        }
        else {
            header.html(`${header.data('edit')} ${name}`);
            $('button#delete').css('display', 'inline-block');
        }

        $.get(`payment/${id}`, function (data) {
            console.log(`GOT payment ${id} modal form`);
            $('#paymentModal .content').html(data);
            $('#paymentModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#paymentModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button.subscriptions').click(function(event) {
        const header = $('#subscriptionModal .header');
        const id = $(event.target).data('id');
        const name = $(event.target).data('name');

        if(id === 0) {
            header.html(`${header.data('add')}`);
            $('button#delete').css('display', 'none');
        }
        else {
            header.html(`${header.data('edit')} ${name}`);
            $('button#delete').css('display', 'inline-block');
        }

        $.get(`subscription/${id}`, function (data) {
            console.log(`GOT payment ${id} modal form`);
            $('#subscriptionModal .content').html(data);
            $('#subscriptionModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#subscriptionModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button.bookings').click(function(event) {
        const header = $('#bookingModal .header');
        const id = $(event.target).data('id');
        const name = $(event.target).data('name');

        if(id === 0) {
            header.html(`${header.data('add')}`);
            $('button#delete').css('display', 'none');
            $('button#confirm').css('display', 'none');
        }
        else {
            header.html(`${header.data('edit')} ${name}`);
            $('button#delete').css('display', 'inline-block');
            $('button#confirm').css('display', 'inline-block');
        }

        $.get(`booking/${id}`, function (data) {
            $('#bookingModal .content').html(data);
            $('#bookingModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#bookingModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button.roles').click(function(event) {
        const header = $('#roleModal .header');
        const id = $(event.target).data('id');
        const name = $(event.target).data('name');

        if(id === 0) {
            header.html(`${header.data('add')}`);
            $('button#delete').css('display', 'none');
        }
        else {
            header.html(`${header.data('edit')} ${name}`);
            $('button#delete').css('display', 'inline-block');
        }

        $.get(`role/${id}`, function (data) {
            $('#roleModal .content').html(data);
            $('#roleModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#roleModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button#delete').click(function (event){
        $('#deleteModalForm').submit();
    })

    $('button#confirm').click(function (event) {
        $('#confirmBookingForm').submit();
    })
});
