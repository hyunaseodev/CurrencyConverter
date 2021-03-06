var main = {
  init: function() {
    selectRecipientCountry();
  }
};

function selectRecipientCountry() {
  clearFields();

  findExchangeRate($("#toCurrency option:selected").val());
}

function findExchangeRate(toCurrency) {
  $.ajax({
    type: 'GET',
    url: '/exchangeRate',
    data: {'toCurrency': toCurrency},
    dataType: 'json',
    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
    success: function(result){
        setExchangeRateData(result);
    },
    error: function(error){
      alert("오류가 발생했습니다.");
    }
  })
}

function submitReceivableAmount() {
  clearReceivableAmountField();

  var toCurrency = $("#toCurrency option:selected").val();
  var sendAmount = $("#sendAmount").val();
  if(sendAmount != '')  sendAmount = parseFloat(sendAmount.replace(/,/g, ''));

  if(checkInvalidSendAmount(sendAmount)) return false;

  $.ajax({
    type: 'GET',
    url: '/receivableAmount',
    data: {'toCurrency': toCurrency, 'sendAmount': sendAmount},
    dataType: 'json',
    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
    success: function(result){
        setExchangeRateData(result);
        setReceivableAmount(result);
    },
    error: function(){
      alert("오류가 발생했습니다.");
    }
  })
}

function setExchangeRateData(resultData) {
  $("#exchangeRate").text(formatNumber(resultData.exchangeRate));
  $("#exchangeRateUnit").text(resultData.toCurrency + "/" + resultData.fromCurrency);
}

function setReceivableAmount(resultData) {
  $("#receivableAmount").text("수취금액은 " + formatNumber(resultData.receivableAmount) + " " + resultData.toCurrency + " 입니다.");
}

function formatSendAmount(numberValue) {
  clearReceivableAmountField();

  $("#sendAmount").val(formatNumber(numberValue));
}

function formatNumber(numberValue) {
  if(typeof(numberValue) == "string") numberValue = parseFloat(numberValue.replace(/,/g, ''));

  if(isNaN(numberValue)) return '';
  else return numberValue.toLocaleString('en-US', { maximumFractionDigits: 2, minimumFractionDigits: 2 });
}

function checkInvalidSendAmount(sendAmount) {
  if(sendAmount == '') {
    alert("송금액을 입력하세요.");
    return true;
  } else if(sendAmount < 0 || sendAmount > 10000) {
    alert("송금액은 0 이상 10,000 USD 이하 금액만 입력 가능합니다.");
    return true;
  } else if(isNaN(sendAmount)) {
    alert("송금액이 바르지 않습니다.");
    return true;
  }
}

function clearFields() {
  $("#exchangeRate").text("");
  $("#exchangeRateUnit").text("");
  $("#sendAmount").value = "";
  $("#receivableAmount").text("");
}

function clearReceivableAmountField() {
  $("#receivableAmount").text("");
}

main.init();