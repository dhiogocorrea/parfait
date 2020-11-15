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
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        [HttpPost]
        public async Task<IActionResult> Search(string stringSearch)
        {
            //HttpClient client = new HttpClient();
            //client.BaseAddress = new Uri("http://169.62.157.212:1992/product/me");

            //string urlParameters = $"?terms={stringSearch}";
            //HttpResponseMessage response = client.GetAsync(urlParameters).Result;

            //if (response.IsSuccessStatusCode)
            //{
            //    var dataObjects =  await response.Content.ReadAsStringAsync();
            //}

            return View();
        }
    }
}
