using Microsoft.AspNetCore.Mvc;

namespace Web.Controllers
{
    public class AccountController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult SignIn()
        {
            return View();
        }

        public string CheckIsLogin()
        {
            string sessionName =  HttpContext.Session.GetString("Username");
            return "Session name is: " + sessionName;
        }
    }
}
