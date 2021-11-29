function success() {
    if(document.getElementById("value").value==="") { 
           document.getElementById('set').disabled = true; 
       } else { 
           document.getElementById('set').disabled = false;
       }
   }