function cateFn(){
	// 카테고리 분류
	var category1 = document.getElementById('category1');
	var cate      = category1.options[category1.selectedIndex].value;
	
	$.ajax({
		url:'/Kmarket/admin/register/selectCate2',
		method:'GET',
		data: { "cate1" : cate },
		dataType:'JSON',
		success: function(data){
			$('#category2').empty();
			
			let tags = "<option value='0'>2차 분류 선택</option>"
			
			$(data.result).each(function(){
				tags += '<option value='+this.cate2+'>'+this.c2Name+'</option>';
			});
			
			$('#category2').append(tags);
		}
	});
	
};

// 가격, 할인율을 통해 포인트, 판매가격 자동 계산
$(function(){
	var price    = document.getElementById('price');
	var discount = document.getElementById('discount');

	price.addEventListener('focusout', function(){
	
		let calcPoint = price.value * 0.01;
	
		$('#point').val(Math.round(calcPoint));
	});	
	
	discount.addEventListener('focusout', function(){
		
		let sellPrice = 0;
		
		if(discount.value == 0){
			sellPrice = price.value;
		}else{
			sellPrice = price.value * (100-(discount.value))/100;	
		}
		
		$('#sellPrice').val(Math.round(sellPrice / 10) * 10 );
	});
});