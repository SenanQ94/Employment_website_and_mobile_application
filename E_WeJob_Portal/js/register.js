function signupCompany(){  
    console.log('first');
    var name = document.getElementById("name").value;
    var phone = document.getElementById("phone").value;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    const url = 'http://sinanqarhiely-001-site1.etempurl.com/EWeJob.svc/SignUpCompany/?name='+name+ '&phone='+ phone +'&username=' + username+ '&password='+password;
    console.log(url);
    console.log(name, phone, username, password);
    $.getJSON(url, function (data1) {
      console.log('here');
      if (data1.code == 1)
       {
         var d = data1.data
                  
            document.getElementById("result").innerHTML = data1.message;
          
        }	
        else{
            document.getElementById("result").innerHTML = data1.message;
        }
    });
        }

function signupCandidate(){  
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    const url = 'http://sinanqarhiely-001-site1.etempurl.com/EWeJob.svc/login/?userType=0&username=' + username +'&password=' + password;

    $.getJSON(url, function (data1) {
        console.log('here');
        if (data1.code == 1)
        {
        document.getElementById("result").innerHTML = '';
            var d = data1.data
            
            document.getElementById("result").innerHTML += "id: " + d.p_id + "</br>";
            document.getElementById("result").innerHTML += "full_name: " + d.full_name + "</br>";
            document.getElementById("result").innerHTML += "phone: " + d.phone + "</br>";
            document.getElementById("result").innerHTML += "edu_field: " + d.edu_field + "</br>";
            document.getElementById("result").innerHTML += "exp_lev: " + d.exp_lev + "</br>";
            document.getElementById("result").innerHTML += "exp_years: " + d.exp_years + "</br>";
        
        }	
        else{
            document.getElementById("result").innerHTML = data1.message;
        }
    });
        }

