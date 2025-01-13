using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using Web.Models2;

namespace Web.Controllers
{
    public class AccountController : Controller
    {
        private MaintainManagementContext context = new MaintainManagementContext();
        // sign in part 
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
            if (IsLogin())
            {
                return RedirectToAction("Index", "Home");
            }
            else
            {
                return RedirectToAction("SignIn");
            }
        }
        public IActionResult AddAccount()
        {
            return View();
        }
        public IActionResult CheckAccount(Account model)
        {
            if (checkExistAccount(model) != null)
            {
                HttpContext.Session.SetString("TheSession", "hasSession");
                HttpContext.Session.SetString("UserName", model.UserName);
                return RedirectToAction("Index", "Home");
            }
            ViewBag.ErrorMessage = "Wrong User name or password, try again";
            return View("SignIn");
        }

        // sign up part
        public IActionResult SignUp()
        {
            return View();
        }
        public IActionResult CheckAccountAfterSignUp(Account account)
        {
            if (FindAccountByUserName(account.UserName) != null)
            {
                ViewBag.ErrorMessage = "User name already exsit, try again";
                return View("SignUp");
            }
            if (account.Password.Length < 6)
            {
                ViewBag.ErrorMessage = "Password must >= 6 character";
                return View("SignUp");
            }
            CreateAccount(account);
            TempData["SuccessMessage"] = "Sign Up successfully, sign in to continue";
            return RedirectToAction("SignIn");
        }

        // log out part
        public IActionResult LogOut()
        {
            HttpContext.Session.Remove("TheSession");
            return RedirectToAction("CheckIsLogin");
        }
        // change password part
        public IActionResult ChangePassword()
        {
            return View();
        }
        [HttpPost]
        public IActionResult CheckPassword(string UserName, string OldPass, string NewPass)
        {
            if (OldPass == NewPass)
            {
                ViewBag.ErrorMessage = "new password must different from old password";
                return View("ChangePassword");
            }
            if (checkExistAccount(new Account(UserName, OldPass)) == null)
            {
                ViewBag.ErrorMessage = "Wrong old password";
                return View("ChangePassword");
            }

            if (NewPass.Length < 6)
            {
                ViewBag.ErrorMessage = "Password must >= 6 character";
                return View("ChangePassword");
            }
            ChangePasswordByUserName(UserName, NewPass);
            ViewBag.SuccessMessage = "Change password successfully";
            return View("ChangePassword");
        }
        // reset password part
        public IActionResult ResetPassword()
        {
            return View();
        }
        public IActionResult DoResetPassword(string UserName)
        {
            if (FindAccountByUserName(UserName) == null)
            {
                ViewBag.ErrorMessage = "User name doesn't exsit, try again";
                return View("ResetPassword");
            }
                ChangePasswordByUserName(UserName, "123456");
                TempData["SuccessMessage"] = "Reset password to 123456, sign in to continue";
                return View("SignIn");
        }
        // other support method part

        [HttpPost]
        [ValidateAntiForgeryToken] // Yêu cầu token phải hợp lệ
        public IActionResult DeleteAccount(string username)
        {
            var account = FindAccountByUserName(username);
            if (account != null)
            {
                context.Accounts.Remove(account);
                context.SaveChanges();
                TempData["SuccessMessage"] = "Account deleted successfully.";
            }
            else
            {
                TempData["ErrorMessage"] = "Account not found.";
            }
            return RedirectToAction("Index", "Home");
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult AddAccount(Account account)
        {
            if (FindAccountByUserName(account.UserName) != null)
            {
                ViewBag.ErrorMessage = "User name already exists, please choose another one.";
                return View();
            }

            if (string.IsNullOrEmpty(account.Password) || account.Password.Length < 6)
            {
                ViewBag.ErrorMessage = "Password must be at least 6 characters long.";
                return View();
            }

            CreateAccount(account);
            TempData["SuccessMessage"] = "Account added successfully.";
            return RedirectToAction("Index", "Home");
        }
        public void ChangePasswordByUserName(string username, string password)
        {
            Account account = FindAccountByUserName(username);
            if (account != null)
            {
                account.Password = password;
                context.SaveChanges();
            }
        }
        public Account FindAccountByUserName(string username)
        {
            return context.Accounts.FirstOrDefault(x => x.UserName == username);
        }
        public void CreateAccount(Account newAcc)
        {
            context.Accounts.Add(newAcc);
            context.SaveChanges();
        }
        private bool IsLogin()
        {
            string? CurrentSessionValue = HttpContext.Session.GetString("TheSession");
            return !String.IsNullOrEmpty(CurrentSessionValue);
        }
        public Account checkExistAccount(Account account)
        {
            List<Account> accounts = context.Accounts.ToList();
            foreach(Account acc in accounts)
            {
                if(acc.UserName == account.UserName && acc.Password == account.Password)
                {
                    return acc;
                }
            }
            return null;
        }
    }
}
