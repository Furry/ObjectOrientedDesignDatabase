function success() {
    if (document.getElementById("value").value === "") { 
           document.getElementById('set').disabled = true; 
        } else { 
           document.getElementById('set').disabled = false;
    }
}

window.addEventListener("click", async (elem) => {
    if (!elem.target) return;
    console.log(elem.target.id);
    switch (elem.target.id) {
        case "find":
            await find();
        break;
        
        case "set":
            await set();
        break;
    }
})

async function find() {
    const key = document.getElementById("txtName").value;
    const value = document.getElementById("valName").value;
    if (key == "") return;

    const keyElem = document.getElementById("keyInfo");
    const valElem = document.getElementById("valueInfo");
    const lineElem = document.getElementById("lineInfo");

    const res = await fetch(`http://localhost:8080/get?key=${key}`);
    const text = await res.text();

    keyElem.innerText = key,
    valElem.innerText = text,
    lineElem.innerText = "Not Implemented Yet."
}

async function set() {
    const key = document.getElementById("txtName").value;
    const value = document.getElementById("valName").value;
    if (key == "" || value == "") return;

    const res = await fetch(`http://localhost:8080/set?key=${key}&value=${value}`);
    const text = await res.text();

    alert(text);
}