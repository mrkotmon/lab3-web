window.onload = init;
window.redrawCanvas = redrawCanvas;
const CANVAS_SIZE = 300;
const C = CANVAS_SIZE / 2;   // 150
const SCALE = 100;           
const HALF = SCALE / 2;      // 50

function drawCanvas1() {
    const canvas = document.getElementById('canvas1');
    canvas.width = CANVAS_SIZE;
    canvas.height = CANVAS_SIZE;

    if (!canvas.getContext) return;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.rect(C - HALF, C - SCALE, HALF, SCALE);
    ctx.fillStyle = 'rgba(91,136,255,0.82)';
    ctx.fill();
}

function drawCanvas2() {
    const canvas = document.getElementById('canvas2');
    canvas.width = CANVAS_SIZE;
    canvas.height = CANVAS_SIZE;

    if (!canvas.getContext) return;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.moveTo(C, C - SCALE);   // (0, R)
    ctx.lineTo(C, C);           // (0, 0)
    ctx.lineTo(C + HALF, C);    // (R/2, 0)
    ctx.closePath();
    ctx.fillStyle = 'rgba(91,136,255,0.82)';
    ctx.fill();
}

function drawCanvas3() {
    const canvas = document.getElementById('canvas3');
    canvas.width = CANVAS_SIZE;
    canvas.height = CANVAS_SIZE;

    if (!canvas.getContext) return;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.beginPath();
    ctx.arc(C, C, HALF, 0, 0.5 * Math.PI);
    ctx.lineTo(C, C);
    ctx.closePath();
    ctx.fillStyle = 'rgba(91,136,255,0.82)';
    ctx.fill();
}

function redrawCanvas() {
    clearDots();
    drawCanvas1();
    drawCanvas2();
    drawCanvas3();

    const r = getCurrentR();
    if (!Number.isFinite(r) || r === 0) return;

    drawDotsFromBeanTableData(r);
    updateR(r);
}

function init() {
    redrawCanvas();
    document.querySelector(".axis-box").addEventListener('click', handleAxisBoxClick);
}

function updateXRadio(x) {
  // округляем к ближайшему целому из [-4..4]
  let xr = Math.round(x);
  if (xr < -4) xr = -4;
  if (xr > 4) xr = 4;

  const radio = document.querySelector(`input[name="mainForm:xRadio"][value="${xr}"]`);
  if (radio) {
    radio.checked = true;
    // полезно триггернуть change (если где-то слушаешь)
    radio.dispatchEvent(new Event('change', { bubbles: true }));
  }

  // (опционально) держим hidden синхронно
  updateHiddenField("mainForm:xHidden", x);
}


function handleAxisBoxClick(event) {
    const axisBox = document.querySelector(".axis-box");
    const rect = axisBox.getBoundingClientRect();

    const clickX = event.clientX - rect.left;
    const clickY = event.clientY - rect.top;

    let r = getCurrentR();
    if (!Number.isFinite(r) || r <= 0) {
        r = 1;
        updateR(r);
    }

    let x = ((clickX - C) / SCALE) * r;
    let y = -((clickY - C) / SCALE) * r;

    const X = normalizeForSubmit(x, 3);
    const Y = normalizeForSubmit(y, 3);

    x = X.num;
    y = Y.num;

    if (x < -4 || x > 4 || y < -5 || y > 3) {
      alert(`Точка (${x.toFixed(2)}, ${y.toFixed(2)}) вне допустимого диапазона\nX: [-4; 4], Y: [-5; 3]`);
      return;
    }

    const yInput = document.getElementById("mainForm:yInput");
    if (yInput) yInput.value = Y.str;

    updateHiddenField("mainForm:xHidden", X.str);
    updateHiddenField("mainForm:yHidden", Y.str);
    updateHiddenField("mainForm:rHidden", String(r));

    updateR(r);
    updateXRadio(x);

    setTimeout(() => {
    const btn = document.getElementById("mainForm:submitCanvas");
    if (btn) btn.click(); }, 10);



}

function onCanvasAjax(data) {
  if (data.status === "success") {
    redrawCanvas();
  }
}





function normalizeForSubmit(v, digits = 3) {
  let n = Number(v);
  if (!Number.isFinite(n)) return { num: NaN, str: "" };

  const factor = Math.pow(10, digits);
  n = Math.round(n * factor) / factor;

  // убираем -0 и очень маленькие значения
  if (Math.abs(n) < 1 / factor) n = 0;
  if (Object.is(n, -0)) n = 0;

  return { num: n, str: (n === 0 ? "0" : String(n)) };
}


function roundLikeServer(v, digits = 3) {
  let n = Number(v);
  if (!Number.isFinite(n)) return NaN;

  const factor = Math.pow(10, digits);
  n = Math.round(n * factor) / factor;

  if (Math.abs(n) < 1 / factor) n = 0; 
  if (Object.is(n, -0)) n = 0;

  return n;
}


function drawDot(x, y, hit) {
    const axisBox = document.querySelector(".axis-box");
    const rect = axisBox.getBoundingClientRect();
    
    const r = getCurrentR();
    if (!Number.isFinite(r) || r === 0) return;
    
    // Преобразуем X,Y в пиксельные координаты
    const screenX = (x / r) * SCALE + C;
    const screenY = C - (y / r) * SCALE;
    
    const dot = document.createElement('div');
    dot.className = hit ? 'dot hit' : 'dot miss';
    dot.style.left = (rect.left + screenX - 5) + 'px';
    dot.style.top = (rect.top + screenY - 5) + 'px';
    dot.style.width = '10px';
    dot.style.height = '10px';
    dot.style.borderRadius = '50%';
    dot.style.position = 'absolute';
    dot.style.backgroundColor = hit ? 'green' : 'red';
    dot.style.zIndex = '1000';
    
    document.body.appendChild(dot);
}

function sendRequest(x, y, r) {
  updateHiddenField("mainForm:xHidden", x);
  updateHiddenField("mainForm:yHidden", y);
  updateHiddenField("mainForm:rHidden", r);

  setTimeout(() => {
    const btn = document.getElementById("mainForm:submitCanvas");
    if (btn) btn.click();
  }, 10);
}

function updateHiddenField(id, value) {
    const element = document.getElementById(id);
    if (element) {
        element.value = value;
    }
}

function updateYInput(y) {
  const yInput = document.getElementById("mainForm:yInput");
  if (!yInput) return;

  const v = roundLikeServer(y, 3);
  yInput.value = Number.isFinite(v) ? String(v) : "";

  yInput.dispatchEvent(new Event("change", { bubbles: true }));
  yInput.dispatchEvent(new Event("blur", { bubbles: true }));
}



function clearDots() {
    const dots = document.querySelectorAll(".dot");
    dots.forEach(dot => dot.remove());
}

function drawDotsFromBeanTableData(r) {
    const tbody = document.querySelector("table#results-table > tbody");
    if (!tbody || r <= 0) return;
    
    const rows = tbody.querySelectorAll("tr");
    rows.forEach(row => {
        const cells = row.querySelectorAll("td");
        if (cells.length >= 4) {
            try {
                const x = parseFloat(cells[0].textContent);
                const y = parseFloat(cells[1].textContent);
                const resultR = parseFloat(cells[2].textContent);
                const isHit = cells[3].textContent.trim() === 'пробитие';
                
                if (!isNaN(x) && !isNaN(y) && !isNaN(resultR) && resultR > 0) {
                    // Используем текущий R для масштабирования, но исходные значения из таблицы
                    drawDot(x, y, isHit);
                }
            } catch (e) {
                console.error("Ошибка при отрисовке точки:", e);
            }
        }
    });
}

function syncXFromRadio(data) {
    if (data.status !== "success") return;
    const x = document.querySelector('input[name="mainForm:xRadio"]:checked')?.value;
    if (x != null) {
        updateHiddenField("mainForm:xHidden", x);
    }
}

function syncRFromRadio(data) {
    if (data.status !== "success") return;
    const r = document.querySelector('input[name="mainForm:rRadio"]:checked')?.value;
    if (r != null) {
        updateHiddenField("mainForm:rHidden", r);
        redrawCanvas();
    }
}

function getCurrentR() {
    const radio = document.querySelector('input[name="mainForm:rRadio"]:checked');
    if (radio && radio.value) {
        return parseFloat(radio.value);
    }
    
    const hidden = document.getElementById("mainForm:rHidden");
    if (hidden && hidden.value !== "") {
        return parseFloat(hidden.value);
    }
    
    return NaN;
}

function updateR(value) {
    const rNum = parseFloat(value);
    if (isNaN(rNum) || rNum < 1) return;
    
    const radio = document.querySelector(`input[name="mainForm:rRadio"][value="${rNum}"]`);
    if (radio) {
        radio.checked = true;
    }
    
    updateHiddenField("mainForm:rHidden", rNum);
    
    updateAxisLabels(rNum);
}

function updateAxisLabels(r) {
    const marks = document.querySelectorAll('.mark');
    marks.forEach(mark => {
        let text = mark.textContent;
        if (text === 'R') mark.textContent = r.toFixed(1);
        else if (text === 'R/2') mark.textContent = (r/2).toFixed(1);
        else if (text === '-R/2') mark.textContent = (-r/2).toFixed(1);
        else if (text === '-R') mark.textContent = (-r).toFixed(1);
    });
}