(function ($) {
    "use strict";
    $('.btnAddToCart').click(function (event) {
        event.preventDefault();
        debugger
        if (!isLogin()) {
            $.toast({
                heading: 'Lỗi thao tác !',
                text: 'Bạn cần đăng nhập tài khoản.',
                position: 'top-right',
                icon: 'error'
            })
            return;
        }
        const productId = $(this).attr('data-product-id');
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        const qty = $("#data-product-qty").val();
        let quantity = 1;
        if(qty){
            quantity = qty;
        }

        $.ajax({
            url: `${window.location.origin}/api/v1/product/add`,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            type: "POST",
            data: JSON.stringify({quantity: quantity, productId: productId}),
            contentType: "application/json",

            success: function (response) {
                const sum = +response;
                //update cart
                // $("#sumCart").text(sum)
                //show message
                $.toast({
                    heading: 'Giỏ hàng',
                    text: 'Thêm sản phẩm vào giỏ hàng thành công',
                    position: 'top-right',
                })
            },
            error: function (response) {
                console.log({response})
                $.toast({
                    heading: 'Lỗi thao tác !',
                    text: 'Product\'s quantity is less than 0!',
                    position: 'top-right',
                    icon: 'error'
                })
            }
        });
    });

    function isLogin() {
        const elementLogin = $('.isLogin');
        if (elementLogin?.length > 0) {
            return true;
        }
        return false;
    }

    $(document).ready(function (){
        $('#btn-filter').click(function (event){
            console.log("click filter")
            event.preventDefault();
            let targetArray = [];
            let priceArray = [];

            // Target Filter //
            $("#target-filter .form-check-input:checked").each(function () {
                targetArray.push($(this).val());
            });
            // Target Filter //
            $("#between-price .form-check-input:checked").each(function () {
                priceArray.push($(this).val());
            });

            let sortValue = $('input[name="sort-radio"]:checked').val();

            const currentUrl = new URL(window.location.href);
            const searchParams = currentUrl.searchParams;

            searchParams.set('page', '1');
            searchParams.set('sort', sortValue);

            if (targetArray.length > 0) {
                searchParams.set('target', targetArray.join(','));
            } else {
                searchParams.delete('target');
            }

            if (priceArray.length > 0) {
                searchParams.set('price', priceArray.join(','));
            } else {
                searchParams.delete('price');
            }
            window.location.href = currentUrl.toString();
        });

        const params = new URLSearchParams(window.location.search);

        // Set checkboxes for 'target'
        if (params.has('target')) {
            const targets = params.get('target').split(',');
            targets.forEach(factory => {
                $(`#target-filter .form-check-input[value="${factory}"]`).prop('checked', true);
            });
        }

        // Set checkboxes for 'factory'
        if (params.has('price')) {
            const prices = params.get('price').split(',');
            prices.forEach(price => {
                $(`#between-price .form-check-input[value="${price}"]`).prop('checked', true);
            });
        }


        // Set radio buttons for 'sort'
        if (params.has('sort')) {
            const sort = params.get('sort');
            $(`input[type="radio"][name="sort-radio"][value="${sort}"]`).prop('checked', true);
        }

    })
})(jQuery);