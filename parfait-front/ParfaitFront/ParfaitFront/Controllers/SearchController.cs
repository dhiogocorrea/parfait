using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using ParfaitFront.Models;

namespace ParfaitFront.Controllers
{

    public class SearchController : Controller
    {
        private string tokenUser;
        private readonly ILogger<HomeController> _logger;

        public SearchController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        [HttpGet]
        public async Task<IActionResult> Index(string stringSearch, string token)
        {
            var url = "http://169.62.157.212:1992/product/me";
            if (string.IsNullOrEmpty(stringSearch))
            {
                url += $"?terms={stringSearch}";
            }

            using (var http = new HttpClient())
            {
                http.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
                var response = http.GetAsync(url).Result;

                if (response.IsSuccessStatusCode)
                {
                    var data = response.Content.ReadAsStringAsync().Result;

                    List<ProductModel> model = Newtonsoft.Json.JsonConvert.DeserializeObject<List<ProductModel>>(data);

                    ViewBag.token = token;
                    return View(model);
                };

            }

            return View();

        }

        public string TryOnImage(string productId, string token)
        {

            var url = "http://169.62.157.212:1992/users/tryon/"+productId;

            using (var http = new HttpClient())
            {
                http.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", token);
                var response = http.GetAsync(url).Result;

                if (response.IsSuccessStatusCode)
                {
                    var data = response.Content.ReadAsStringAsync().Result;

                    return data.Replace("\"", "");
                };

            }

            return "";
        }
    }
}
