//  sort q=0 years
//  sort q=1 level



$(document).ready(function() {
    $('#home').click();
 });

function getJobs(){ 
var j_div = document.getElementById('home');
j_div.innerHTML="";
const url = 'http://sinanqarhiely-001-site1.etempurl.com/EWeJob.svc/getAllJobs/?q=1';

$.getJSON(url, function (data1) {
console.log('here');
if (data1.code == 1)
{
    var d = data1.data;

$.each(d, function (key, entry) {

    j_div.innerHTML +='<div class="tab_grid"> <div class="jobs-item with-thumb"> <div class="jobs_right"> <div class=" date_desc"> <h4 class="title" style="color:#fc8168 ;  text-transform: capitalize;">' + entry.title +' </h4><span class="meta"> <h4 style="color:rgb(85, 22, 167) ;  text-transform: capitalize;">' + entry.comName+ '</h4></span></div><div class="clearfix"> </div><ul class="top-btns"><li id="salary" style="color :#222f50 ;">'+ entry.salary+ ' SYP</li><li><a href="#" class="fa fa-plus toggle"></a></li></ul><h6 class="description" id="educ_f" style="color:#e42b91 ;  text-transform: capitalize;"> Required Education Field: ' + entry.field + ' </h6><h6 class="description" id="educ_l" style="color:#e42b91 ; text-transform: capitalize;"> Required Education Diploma: ' + entry.level + ' </h6><h6 class="description" id="exp_l" style="color:#e42b91 ; text-transform: capitalize;"> Required Experience Years:  ' + entry.years + ' YEARS </h6></div><div class="clearfix"> </div></div></div>';
})
}
});
}

function getYourJobs(){ 
    var j_div1 = document.getElementById('profile');
    j_div1.innerHTML="";
    const url = 'http://sinanqarhiely-001-site1.etempurl.com/EWeJob.svc/getspecJobs/?q=1&userId=2';
    
    $.getJSON(url, function (data1) {
    console.log('here');
    if (data1.code == 1)
    {
        var d = data1.data;
    
    $.each(d, function (key, entry) {

        j_div1.innerHTML +='<div class="tab_grid"> <div class="jobs-item with-thumb"> <div class="jobs_right"> <div class=" date_desc"> <h4 class="title" style="color:#fc8168 ;  text-transform: capitalize;">' + entry.title +' </h4><span class="meta"> <h4 style="color:rgb(85, 22, 167) ;  text-transform: capitalize;">' + entry.comName+ '</h4></span></div><div class="clearfix"> </div><ul class="top-btns"><li id="salary" style="color :#222f50 ;">'+ entry.salary+ ' SYP</li><li><a href="#" class="fa fa-plus toggle"></a></li></ul><h6 class="description" id="educ_f" style="color:#e42b91 ;  text-transform: capitalize;"> Required Education Field: ' + entry.field + ' </h6><h6 class="description" id="educ_l" style="color:#e42b91 ; text-transform: capitalize;"> Required Education Diploma: ' + entry.level + ' </h6><h6 class="description" id="exp_l" style="color:#e42b91 ; text-transform: capitalize;"> Required Experience Years:  ' + entry.years + ' YEARS </h6></div><div class="clearfix"> </div></div></div>';
})
}
});
}




