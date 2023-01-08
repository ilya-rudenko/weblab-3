let x;
let y;
let r = 1;
let rElement = document.getElementById("controls-form:r-value");
let submitButton = document.getElementById("controls-form:submitButton");

$(function (){

    let submitButton = document.getElementById("controls-form:submitButton");
    let canvasCurrent = $("#canvas")[0].getContext("2d");

    $(document.getElementById("controls-form:x-value")).val(0);
    $(document.getElementById("controls-form:y-value")).val(0);

    clearCanvas(canvasCurrent);
    drawGraph(canvasCurrent);
    loadTablePoints(canvasCurrent);

    $(submitButton).click((event) => {
        if (!validateForm()) {
            event.preventDefault();
        } else {
            console.log('DATA SEND');
        }
    });

    $('.rButton').on('change',() => {
        let canvasCurrent = $("#canvas")[0].getContext("2d");
        r = document.getElementById("controls-form:r-value").value;

        clearCanvas(canvasCurrent);
        drawGraph(canvasCurrent);
        loadTablePoints(canvasCurrent);
    });

    $('.canvas').click((event)=>{

        let {x,y} = getCursorPosition(document.getElementById('canvas'),event);

        // console.log(x,y);

        x = (x - 150)/20;
        y = (150 - y)/20;

        console.log(x,y);

        $(document.getElementById("controls-form:x-value")).val(x);
        $(document.getElementById("controls-form:y-value")).val(y);
        $(document.getElementById("controls-form:submitButton")).click();
    });

});

const handleSubmit = () => {
    let canvasCurrent = $("#canvas")[0].getContext("2d");
    clearCanvas(canvasCurrent);
    drawGraph(canvasCurrent);
    loadTablePoints(canvasCurrent);
}

function validateX() {
    x = $('.x-value')[0].value;
    // console.log("X: ",x);

    if (isNumeric(x) && parseFloat(x) >= -5 && parseFloat(x) <= 5) return true;
    alert('Некорректное значение X');
    return false;
}

function validateY() {
    y = $('.input_form__y')[0].value;
    // console.log("Y: ",y);

    if (isNumeric(y) && y >= -3 && y <= 3) return true;
    alert('Некорректное значение Y');
    return false;
}

function validateR() {
    r = document.getElementById("controls-form:r-value").value;
    // console.log("R: ",r);

    if (isNumeric(r) && r>0 && r<6) return true;
    alert("Некорректное значение R");
    return false;
}

function validateForm() {
    return validateX() && validateY() && validateR();
}

function isNumeric(x) {
    return !isNaN(parseFloat(x)) && isFinite(x);
}

const canvas_arrow = (context, fromx, fromy, tox, toy) => {
    let headlen = 10; // length of head in pixels
    let dx = tox - fromx;
    let dy = toy - fromy;
    let angle = Math.atan2(dy, dx);
    context.moveTo(fromx, fromy);
    context.lineTo(tox, toy);
    context.lineTo(tox - headlen * Math.cos(angle - Math.PI / 6), toy - headlen * Math.sin(angle - Math.PI / 6));
    context.moveTo(tox, toy);
    context.lineTo(tox - headlen * Math.cos(angle + Math.PI / 6), toy - headlen * Math.sin(angle + Math.PI / 6));
}

const clearCanvas = (ctx) => {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

const drawGraph = (ctx) => {
    ctx.strokeStyle = "rgba(0, 0, 0, 1)";
    ctx.fillStyle = "rgba(0, 0, 0, 1)";

    ctx.beginPath();
    // Y
    canvas_arrow(ctx,150,275,150,25);

    // X
    canvas_arrow(ctx,25,150,275,150);
    ctx.stroke();

    ctx.font = "15pt Yandex Sans";

    ctx.fillText("x", 275, 150);
    ctx.fillText("y", 150, 25);

    ctx.fillText("5",150,50);
    ctx.fillText("5",250,150);

    ctx.fillText("-5",150,250);
    ctx.fillText("-5",50,150);

    ctx.fillText("2.5",150,100);
    ctx.fillText("2.5",200,150);

    ctx.fillText("-2.5",150,200);
    ctx.fillText("-2.5",100,150);

    // GRAPH
    // center 150,150
    // 0-5: 100, 1=20;
    // r=5 - 1,

    ctx.fillStyle = "rgba(50, 63, 179, 0.5)";

    // RECTANGLE
    ctx.fillRect(150,150,20*r,-20*r/2);

    // CIRCLE
    ctx.beginPath();
    ctx.strokeStyle = "rgba(50, 63, 179, 0.5)";
    ctx.moveTo(150,150);
    ctx.arc(150,150,20*r/2,Math.PI,Math.PI*3/2,false);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    // TRIANGLE
    ctx.beginPath();
    ctx.strokeStyle = "rgba(50, 63, 179, 0.5)";
    ctx.moveTo(150-20*r/2,150);
    ctx.lineTo(150,150);
    ctx.lineTo(150,150+20*r);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

    ctx.strokeStyle = "rgba(0, 0, 0, 1)";
    ctx.fillStyle = "rgba(0, 0, 0, 1)";
}

function loadTablePoints(ctx) {
    let rows = [];
    let headers = $(".result-table th");

    $(".result-table tr").each(function(index) {
        let cells = $(this).find("td");
        // console.log(cells);
        rows[index] = {};
        cells.each(function(cellIndex) {
            rows[index][$(headers[cellIndex]).html()] = $(this).html().replace(/\s/g, "");
        });
    });

    for (let i = 0; i < rows.length; i++) {
        drawTablePoint(
            ctx,
            rows[i]['X'],
            rows[i]['Y'],
            rows[i]['Результат']);
    }
}

function drawTablePoint(ctxPoints, x, y, hitResult) {
    // console.log({x,y,hitResult});

    ctxPoints.fillStyle = hitResult==="true" ? 'green' : 'red';
    ctxPoints.beginPath();
    ctxPoints.arc(
        150 + x*20,
        -y*20 + 150,
        4, 0, 2 * Math.PI);
    ctxPoints.fill();
}

function getCursorPosition(canvas, event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left
    const y = event.clientY - rect.top
    return {x,y};
}






