$(function(){
    var initUrl = '/onlineStore/shopadmin/getshopinitinfo';
    var registerShopUrl = '/onlineStore/shopadmin/registershop';
    getShopInitInfo();

    function getShopInitInfo(){
        $.getJSON(initUrl, function(data){
            if(data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function(item, index){
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function(item, index){
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                })
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });

        $('#submit').click(function(){
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('shop-phone').val();
            shop.shopDesc = $('shop-desc').val();
            shop.shopCategory = {
                shopCategoryId : $('#shop-category').find('option').not(function(){
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId: $('#area').find('option').not(function(){
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            var verifyCodeActual = $('#j_captcha').val();
            if(!verifyCodeActual){
                $.toast('please input captcha')
                return;
            }
            formData.append('shopImg', shopImg);
            formData.append('shopStr', JSON.stringify(shop));
            formData.append('verifyCodeActual', verifyCodeActual);
            $.ajax({
                url: registerShopUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                proceesData: false,
                cache: false,
                success: function(data) {
                    if (data.success) {
                        $.toast('Submit Success !');
                    } else {
                        $.toast('Submit Fail !' + data.errMsg);
                    }
                }
                $('#captcha_img').click();

            });
        })
    }
})