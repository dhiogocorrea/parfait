chrome.runtime.onInstalled.addListener(function () {
  chrome.declarativeContent.onPageChanged.removeRules(undefined, function () {
    chrome.declarativeContent.onPageChanged.addRules([{
      conditions: [new chrome.declarativeContent.PageStateMatcher({
        pageUrl: { hostEquals: "lojasrenner.com.br" },
      }), new chrome.declarativeContent.PageStateMatcher({
        pageUrl: { hostEquals: "www.lojasrenner.com.br" },
      })
      ],
      actions: [new chrome.declarativeContent.ShowPageAction()]
    }]);

  });

});