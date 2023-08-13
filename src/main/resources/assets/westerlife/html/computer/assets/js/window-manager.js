/*

    Author:     Gabidut76
    For:        WesterLife
    Date:       2023-03-10

*/

/*

Attention, propriété de WesterLife, toute utilisation, modification ou reproduction est interdite sans autorisation claire et explicite de la pars de l'administation de WesterLife est formellement interdite.
Tout manquement à cette règle est passible de poursuite judiciaire.

©️ WesterLife 2023 - Gabidut76, Yan36

*/

let windows = [];

function openWindow(frameLoc) {
    let window = document.createElement("div");
    window.className = "window";
    window.id = "window";

    let titleBar = document.createElement("div");
    titleBar.className = "window-title-bar";
    titleBar.id = "window_head";
    
    let windowName = document.createElement("p");
    windowName.className = "window-name";
    windowName.innerHTML = "Window";
    titleBar.appendChild(windowName);

    let alignedRight = document.createElement("div");
    alignedRight.className = "window-aligned-right";


    let closeButton = document.createElement("button");
    closeButton.className = "window-close-button";
    closeButton.innerHTML = "X";
    closeButton.onclick = function() {
        window.remove();
    };

    alignedRight.appendChild(closeButton);
    titleBar.appendChild(alignedRight);


    let frame = document.createElement("iframe");
    frame.src = frameLoc;
    frame.className = "window-frame";

    window.appendChild(titleBar);

    window.appendChild(frame);
    document.body.appendChild(window);

    dragElement(window);
}

// https://www.w3schools.com/howto/howto_js_draggable.asp

function dragElement(elmnt) {
    var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    if (document.getElementById(elmnt.id + "_head")) {
        // if present, the header is where you move the DIV from:
        document.getElementById(elmnt.id + "_head").onmousedown = dragMouseDown;
    } else {
        // otherwise, move the DIV from anywhere inside the DIV:
        elmnt.onmousedown = dragMouseDown;
    }

    function dragMouseDown(e) {
        e = e || window.event;
        e.preventDefault();
        // get the mouse cursor position at startup:
        pos3 = e.clientX;
        pos4 = e.clientY;
        document.onmouseup = closeDragElement;
        // call a function whenever the cursor moves:
        document.onmousemove = elementDrag;
    }

    function elementDrag(e) {
        e = e || window.event;
        e.preventDefault();
        // calculate the new cursor position:
        pos1 = pos3 - e.clientX;
        pos2 = pos4 - e.clientY;
        pos3 = e.clientX;
        pos4 = e.clientY;
        // set the element's new position:
        elmnt.style.top = (elmnt.offsetTop - pos2) + "px";
        elmnt.style.left = (elmnt.offsetLeft - pos1) + "px";
    }

    function closeDragElement() {
        // stop moving when mouse button is released:
        document.onmouseup = null;
        document.onmousemove = null;
    }
}