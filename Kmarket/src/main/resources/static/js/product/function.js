$(function(){
	
	// 메인, 상품 영역 베스트 상품 스크롤
	
	$(".slider > ul").bxSlider({
		easing: "linear",
	});
	
    var best = $("aside > .best");

	$(window).scroll(function () {
	    var t = $(this).scrollTop();

    if (t > 620) {
	    best.css({
	        position: "fixed",
	        top: "0",
    	});
    } else {
    	best.css({ position: "static" });
    }
	});
	
});