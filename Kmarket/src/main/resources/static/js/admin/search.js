function getSearchList(){
	$.ajax({
		type: 'GET',
		url : "/Kmarket/admin/product/list",
		data : $("form[name=search-form]").serialize(),
		success : function(result){
			//테이블 초기화
			$('#board > tbody').empty();
			if(result.length>=1){
				result.forEach(function(item){
					str='<tr>'
					str+="<td>"+item.prodNo+"</td>";
					str+="<td>"+item.prodName+"</td>";
					str+="<td>"+item.price+"</td>";
					str+="<td>"+item.discount+"</td>";
					str+="<td>"+item.point+"</td>";
					str+="<td>"+item.stock+"</td>";
					str+="<td>"+item.seller+"</td>";
					str+="<td>"+item.hit+"</td>";
					str+="<td>"+item.date+"</td>";
					str+="<td>"+item.hit+"</td>";
					str+="</tr>"
					$('#board').append(str);
        		})				 
			}
		}
	})
}
