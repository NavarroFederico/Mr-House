$(document).ready(function(){
    $(".dropwdown").click(function(){
        $(".dropdown-list ul").toggleClass("active");
    });

    $(".dropdown-list ul li").click(function(){
        var icon_text = $(this).html();
        $(".default-option").html(icon_text);
    });

    $(document).on("click",function(event){
        if(!$(event.target).closest(".dropdown").lenght){
            $(".dropdown-list ul").removeClass("active")
        }
    });
});