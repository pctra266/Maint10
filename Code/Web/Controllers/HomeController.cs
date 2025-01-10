using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using Web.Models;
using Web.Models2;

namespace Web.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private MaintainManagementContext context = new MaintainManagementContext();
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

        public IActionResult Login()
        {
            return View();
        }

        public IActionResult CheckAccount(Account model)

        {
            List<Account> accounts = context.Accounts.ToList();
            foreach(Account account in accounts)
            {
                if(model.Password.Equals(account.Password) && model.UserName.Equals(account.UserName))
                {
                    return RedirectToAction("Index");
                }
            }
            return RedirectToAction("Login");
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
