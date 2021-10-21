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

    $('button.utilities').click(function(event) {
        const header = $('#utilitiesModal .header');
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

        $.get(`utilities/${id}`, function (data) {
            console.log(`GOT utility ${id} modal form`);
            $('#utilitiesModal .content').html(data);
            $('#utilitiesModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#utilitiesModalForm').submit();
                }
            }).modal('show');
        });
    });

    $('button#delete').click(function (event){
        $('#deleteModalForm').submit();
    })

    $('button#confirm').click(function (event) {
        $('#confirmBookingForm').submit();
    });

    $('button.users').click(function(event) {
        const header = $('#userModal .header');
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

        $.get(`users/${id}`, function (data) {
            console.log(`GOT user ${id} modal form`);
            $('#userModal .content').html(data);
            $('#userModal').modal('setting', {
                closable: false,
                onApprove: function (click) {
                    $('#userModalForm').submit();
                }
            }).modal('show');
        });
    });
});
