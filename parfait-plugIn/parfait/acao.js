let myicon = `<a href="#" class="icon-wish my-icon" style="position: absolute;top: 10px;right: 10px;"></a>`;

let myMatch = ['552454490-552454529', '552450093-552450114', '552450034-552450069']

function observar(element, evento) {
  var targetNode = element//document.getElementById(id);
  var observer = new MutationObserver(evento);
  observer.observe(targetNode, { attributes: true, childList: true });
}

function setIcon() {
  $(".my-icon").remove();
  myMatch.forEach(function (x) {
    console.log(x)
    console.log($(`[data-selector='${x}']>.subject>.img_product`))
    $(`[data-selector='${x}']>.subject>.img_product`).append(myicon)
  })
}

observar(document.querySelector("body"), function () {
  setIcon();
});

$.ajax({
  url: "http://169.62.157.212:1992/product/me",
  method: "GET",
  success: function (d) {
    myMatch = d.map(function (m) { m.productId });
    setIcon();
  },
  erro: function (e) {
    console.log(e)
  }
})


