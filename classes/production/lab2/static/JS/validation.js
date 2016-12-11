$(document).ready(function(){
        $("input").on("keyup", function(){

         var empty = false;
        $('input').each(function() {
            if ($(this).val() == '') {
                empty = true;
            }
        });
         if(!empty)
         {
            $("#submit").removeAttr("disabled");
         }
          else
          {
            $("#submit").attr("disabled", "disabled");
          }
    });
});