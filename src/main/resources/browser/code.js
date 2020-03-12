var App = App || {};
App = (function () {

    var that = {},
        next,
        prev,
        bar,
        frame,
        totalSteps = 10,
        currentStep = 0;

    function init() {
        initIFrame();
        initUI();
        addListeners();
    }

    function initIFrame() {
        frame = document.getElementById("instructions");
        frame.src = "res/content/"+currentStep+".html";
    }

    function initUI() {
        next = document.getElementById("next");
        prev = document.getElementById("previous");
        bar = document.getElementById("bar");

        prev.disabled = true;
        
        initProgress();
    }

    function initProgress() {
        var stepDots = [];
        for (let i = 0; i < totalSteps; i++) {
            let dot = document.createElement("SPAN");
            dot.classList.add("p-dot");
            stepDots.push(dot);
        }
        
        stepDots.forEach(dot => {
            bar.append(dot);       
        });
    }

    function addListeners() {
        next.addEventListener("click", function(){  
            if (currentStep < totalSteps) {
                prev.disabled = false;                
                currentStep++;
                colorDots();
                frame.src = "res/content/"+currentStep+".html";

                if (currentStep > 0) {
                    next.textContent = "Weiter";
                }
            }

            if (currentStep === totalSteps) {
                next.textContent = "Rundgang beenden";
            }      
        })

        prev.addEventListener("click", function() {
            if (currentStep > 0) {
                if (currentStep === totalSteps) {
                    next.textContent = "Weiter";
                }

                if (currentStep === 1) {
                    prev.disabled = true;
                    next.textContent = "Rundgang starten";
                }

                currentStep--;
                colorDots();
                frame.src = "res/content/"+currentStep+".html";
            } 
        })
    }

    function colorDots(){
        bar.childNodes.forEach(node => {
            node.classList.remove("progress");
        });
        for (let i = 0; i < currentStep; i++) {
            bar.childNodes[i].classList.add("progress");
        }
    }

    that.init = init;
    return that;

}());