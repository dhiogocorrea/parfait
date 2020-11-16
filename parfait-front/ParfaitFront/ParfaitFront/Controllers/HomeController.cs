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
        private static Dictionary<string, Dictionary<string, string>> picturesObj = new Dictionary<string, Dictionary<string, string>>();


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

        public IActionResult Login()
        {
            return View();
        }

        [HttpGet]
        public async Task<IActionResult> SendPictures(string id, string front, string side)
        {
            if (picturesObj.ContainsKey(id) && picturesObj.ContainsKey("front") && picturesObj.ContainsKey("side") && !String.IsNullOrEmpty(id) && !String.IsNullOrEmpty(front) && !String.IsNullOrEmpty(side))
            {
                picturesObj[id]["front"] = front;
                picturesObj[id]["side"] = side;
            }
        }

        [HttpGet]
        public async Task<IActionResult> GetNewCaptureId()
        {
            string id = Guid.NewGuid().ToString().Split("-")[0];
            Dictionary<string, string> list = new Dictionary<string, string>();
            list.Add("front", "");
            list.Add("side", "");
            picturesObj.Add(id, list);

            return Ok(id);
        }

        [HttpGet]
        public async Task<IActionResult> VerifyCapture(string id)
        {
            if (picturesObj.ContainsKey(id) && picturesObj[id].ContainsKey("front") && picturesObj[id].ContainsKey("side") && picturesObj[id]["front"] != "" && picturesObj[id]["side"] != "")
            {
                return picturesObj[id];
            } 
            return "";
        }
    }
}
