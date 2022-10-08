// document.getElementById('edu_f');
// document.getElementById('edu_l');

const url = 'http://sinanqarhiely-001-site1.etempurl.com/EWeJob.svc/getDiploma/';

$.getJSON(url, function (data1) {
console.log('here');
if (data1.code == 1)
{
    var d = data1.data
$.each(d, function (key, entry) {

    var field = document.getElementById('edu_f');
    var level =document.getElementById('edu_l');
    field.innerHTML = field.innerHTML +
        '<option value="' + entry.d_field + '">' + entry.d_field + '</option>';
    level.innerHTML = level.innerHTML +
        '<option value="' + entry.d_title + '">' + entry.d_title + '</option>';

})
}
});

