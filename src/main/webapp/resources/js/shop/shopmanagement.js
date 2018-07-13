$(function() {
    var shopId = getQueryString('shopId');
    var shopInfoUrl = '/onlineStore/shopadmin/getahopmanagementinfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function(data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href',
                '/onlineStore/shopadmin/shopoperation?shopId=' + shopId);
        }
    });
});