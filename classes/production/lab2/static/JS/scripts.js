/**
 * Created by akash on 11/15/16.
 */

$(document).ready(function(){
    $("#add-phone").click(function(){
        alert('hi');
        $( "#phone-list" ).append( "<div class='row'><div class='input-field col s12 m12'><input type='text' class='validate'/> <label>Phone</label></div></div>");
    });

    $("#add1-phone").click(function(){
        var index  = $("#phone1-list").length;
        alert('ohter');
        $( "#phone1-list" ).append( "<div class='row'><div class='input-field col s12 m12'><input type='text' th:field='*{phones[__${stat.'+index+'}__].number}' class='validate'/> <label>Phone</label></div></div>");
    });
});
