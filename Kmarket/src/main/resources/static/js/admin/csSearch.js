$(function(){
  $(document).on("change", "select[name=searchType]", ()=>{
    let searchType = $('select > option:selected').val();

    
    console.log(searchType); // 고객서비스, 안전거래

    location.href="/Kmarket/admin/cs/list/notice?searchType="+searchType;

    /*
    $.ajax({
        url:'/Kmarket/admin/cs/list/notice',
        method:'GET',
        data:{ "searchType":searchType},
        dataType:'json',
        success: (data)=>{
            console.log('dfgdf');
        }
    });
    */
  });  
});