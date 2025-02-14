(function ($) {

    $('.btnAddToCart').click(function (event) {
        debugger
        event.preventDefault();

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
        // const token = $("meta[name='_csrf']").attr("content");
        // const header = $("meta[name='_csrf_header']").attr("content");
        const qty = $("#data-product-qty").val();
        let quantity = 1;
        if(qty){
            quantity = qty;
        }

        $.ajax({
            url: `${window.location.origin}/api/v1/product/add`,
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
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
                alert("có lỗi xảy ra, check code đi ba :v")
                console.log("error: ", response);
            }
        });
    });

    function isLogin() {
        const elementLogin = $('.isLogin');
        console.log(elementLogin?.length)
        if (elementLogin?.length > 0) {
            return true;
        }
        return false;
    }
})(jQuery);