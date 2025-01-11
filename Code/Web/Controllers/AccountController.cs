using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using Web.Models2;

namespace Web.Controllers
{
    public class AccountController : Controller
    {
        private MaintainManagementContext context = new MaintainManagementContext();
        public IActionResult SignUp()
        {
            return View();
        }
        public IActionResult CheckAccountAfterSignUp(Account account)
        {
            CreateAccount(account);
            return RedirectToAction("SignIn");
        }
        public void CreateAccount(Account newAcc)
        {
            context.Accounts.Add(newAcc);
            context.SaveChanges();
        }

        public IActionResult SignIn()
        {
            if (IsLogin())
            {
                return RedirectToAction("Index", "Home");
               
            }
            else
            {
                return View();
            }
            
        }

        public IActionResult CheckIsLogin()
        {
            if (IsLogin()) {
                return RedirectToAction("Index", "Home");
                
            }
            else
            {
                return RedirectToAction("SignIn");
            }
            
        }

        public IActionResult ChangePassword()
        {
            return View();
        }

        private bool IsLogin()
        {
            string? CurrentSessionValue = HttpContext.Session.GetString("TheSession");
            return !String.IsNullOrEmpty(CurrentSessionValue);
        }

        public IActionResult CheckAccount(Account model)

        {
            List<Account> accounts = context.Accounts.ToList();
            foreach (Account account in accounts)
            {
                if (model.Password.Equals(account.Password) && model.UserName.Equals(account.UserName))
                {
                    HttpContext.Session.SetString("TheSession", "hasSession");
                    HttpContext.Session.SetString("UserName", model.UserName);

                    return RedirectToAction("Index","Home");
                }
            }
            return RedirectToAction("SignIn");
        }
        [HttpPost]
        public IActionResult CheckPassword(string UserName, string OldPass, string NewPass)
        {
            if(OldPass != NewPass)
            {
                Account account = context.Accounts.FirstOrDefault(a => a.UserName == UserName);
                account.Password = NewPass;
                context.SaveChanges();
                return RedirectToAction("Index","Home");
            }
            return RedirectToAction("ChangePassword");
        }

        public IActionResult LogOut()
        {
            HttpContext.Session.Remove("TheSession");
            return RedirectToAction("CheckIsLogin");
        }
    }
}
