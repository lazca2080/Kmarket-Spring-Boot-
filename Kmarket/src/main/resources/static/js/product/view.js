$(function(){
	// 메인, 상품 영역 베스트 상품 스크롤 -----------------------------------------------------
	$(".slider > ul").bxSlider({ easing: "linear", });
	
    var best = $("aside > .best");

	$(window).scroll(function () {
	    var t = $(this).scrollTop();
	    if (t > 620) { best.css({ position: "fixed", top: "0", });
	    } else { best.css({ position: "static" }); } 
    });
    // 메인, 상품 영역 베스트 상품 스크롤 -----------------------------------------------------
	
	/* 상품 수량 증감 ---------------------------------------------------------------------*/
	// 상품 가격
	var vPrice = document.getElementById('price');
	// 상품 판매 가격
	var vSellPrice = document.getElementById('sellPrice');
	// 상품 최종 가격(판매 가격 + 배송비)
	var vTotalPrice = document.getElementById('totalPrice');
	
	let price      = Number(vPrice.innerText);
	let sellPrice  = Number(vSellPrice.innerText);
	let totalPrice = Number(vTotalPrice.innerText);

	var increase = document.querySelector('button[class=increase]');
	var decrease = document.querySelector('button[class=decrease]');
	var num = document.querySelector('input[name=num]');
	
	/* 증가 버튼 ------------------------------------------------------------------------*/
	increase.addEventListener('click', function(){
		num.setAttribute('value', Number(num.value)+1);
		totalPrice = Number(totalPrice) + sellPrice
		vTotalPrice.innerText = (totalPrice).toLocaleString();
	});
	/* 증가 버튼 ------------------------------------------------------------------------*/
	
	/* 감소 버튼 ------------------------------------------------------------------------*/
	decrease.addEventListener('click', function(){
		if(Number(num.value) > 1){
			num.setAttribute('value', Number(num.value)-1);
			totalPrice = totalPrice - sellPrice
			vTotalPrice.innerText = (totalPrice).toLocaleString();
		}else{
			return false;
		}
	});
	/* 감소 버튼 ------------------------------------------------------------------------*/
	
	/* 원 단위 콤마 -------------------------------------------------------------------- */
	vPrice.innerText      = price.toLocaleString();
	vSellPrice.innerText  = sellPrice.toLocaleString();
	vTotalPrice.innerText = totalPrice.toLocaleString();
	/* 원 단위 콤마 -------------------------------------------------------------------- */
	
	/* 장바구니 ------------------------------------------------------------------------*/	
	var cart = document.getElementById('cart')
	cart.addEventListener('click', function(){
		
		let prodNo   = document.getElementById('prodNo').innerText;   // 상품 번호
		let count    = num.value;                                     // 수량
		price        = vPrice.innerText.split(',').join('');          // 가격
		let discount = document.getElementById('discount').innerText; // 할인 율
		let point    = Math.round(price * 0.01);                      // 적립 포인트
		let delivery = document.getElementById('delivery').innerText; // 배송 비
		if(delivery == '무료배송'){ delivery = 0; }
		totalPrice   = (vTotalPrice.innerText).split(',').join('');   // 최종 가격
		
		// uid는 컨트롤러 or 서비스에서 principal 이용해서 처리
		// input type=hidden을 이용한 처리는 변조의 위험이 있음
		// 마찬가지로 상품과 관련된 모든 정보는 변조의 위험이 있어서 input type=hidden을 사용하지 않음
	
		let jsonData = {
			"type" : 'cart',
			"prodNo" : prodNo,
			"count" : count,
			"price" : price * count,
			"discount" : discount,
			"point" : point,
			"delivery" : delivery,
			"total" : totalPrice
		}
	
		
		$.ajax({
			url:'/Kmarket/product/cart',
			method:'POST',
			data: JSON.stringify(jsonData),
			contentType:'application/json',
			dataType:'JSON',
			success: function(data){
				
				if(data.result == 1){
					if(confirm('장바구니에 상품이 담겼습니다. 확인하시겠습니까?')){
						location.href = "/Kmarket/product/cart"
					}
				}else{
					alert('다시 시도하여 주십시요');
				}
			}
		});
		
	});
	/* 장바구니 ------------------------------------------------------------------------*/
	
	/* 구매하기 ------------------------------------------------------------------------*/	
	var order = document.getElementById('order');
	order.addEventListener('click', function(){
		let prodNo   = document.getElementById('prodNo').innerText;   // 상품 번호
		let count    = num.value;                                     // 수량
		price        = vPrice.innerText.split(',').join('');          // 가격
		let discount = document.getElementById('discount').innerText; // 할인 율
		let point    = Math.round(price * 0.01);                      // 적립 포인트
		let delivery = document.getElementById('delivery').innerText; // 배송 비
		if(delivery == '무료배송'){ delivery = 0; }
		totalPrice   = (vTotalPrice.innerText).split(',').join('');   // 최종 가격
		
		let jsonData = {
			"type" : 'order',
			"prodNo" : prodNo,
			"count" : count,
			"price" : price * count,
			"discount" : discount,
			"point" : point,
			"delivery" : delivery,
			"total" : totalPrice
		}
	
		
		$.ajax({
			url:'/Kmarket/product/purchase',
			method:'POST',
			data:JSON.stringify(jsonData),
			contentType:'application/json',
			dataType:'JSON',
			success: function(data){
				
				if(data.result == 1){
					if(confirm('구매 하시겠습니까?')){
						location.href = "/Kmarket/product/order";
					}
				}else{
					alert('다시 시도하여 주십시요');
				}
			}
		});
	});
	/* 구매하기 ------------------------------------------------------------------------*/
	
	// 배송 예정 날짜 수정 ------------------------------------------------------------------
	// 현재날짜
	let today = new Date();
	
	// 현재 달
	let month = today.getMonth()+1;
	
	// 2일 뒤 날짜 
	let date = today.getDate()+2;
	
	// 2일 뒤 요일
	let day = today.getDay()+2;
	let message;
	
	// 현재 달의 마지막 일
	let lastDay = new Date(today.getFullYear(), today.getMonth()+1, 0);
	
	// 3일 뒤 날짜가 현재 달 마지막 일보다 크면
	if(date > lastDay.getDate()){
		
		// 넘어가는 달 날짜는 = 3일 뒤 날짜 - 현재 달의 마지막 날짜
		// ex) 12월 -> 1월   =   33 - 31 = 2 => 1월 2일
		let nextDate = date-lastDay.getDate();
		
		// day는 연속성을 가짐으로 따로 건드리지않음. date는 30, 31, 28 불연속성이라 보정해줘야함
		
		// 현재 달이 12월이면
		if(today.getMonth()+1 == 12){
			let newDay = new Date(2023, 0);
			month = newDay.getMonth()+1;
			date = nextDate;
			
		// 12월이 아닌 달이면 ex)1월->2월, 2월->3월
		}else{
			let newDay = new Date(today.getFullYear(), today.getMonth()+1);
			month = newDay.getMonth()+1;
			date = nextDate;
		}
	}
	
	if(day == 1 || day == 8){
		message = '월';
	}else if(day == 2 || day == 9){
		message = '화';
	}else if(day == 3 || day == 10){
		message = '수';
	}else if(day == 4){
		message = '목';
	}else if(day == 5){
		message = '금';
	}else if(day == 6){
		message = '토';
	}else if(day == 0 || day == 7){
		message = '일';
	}
	
	
	$('.arrival').text('모레('+message+') '+month+'/'+date+" 도착예정");

	$('#scrollReview').click(function(){
		const offset = $(".review").offset();
    	$('html, body').animate({scrollTop: offset.top}, 500);
	});
	// 배송 예정 날짜 수정 ------------------------------------------------------------------
	
});