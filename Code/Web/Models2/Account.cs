using System;
using System.Collections.Generic;

namespace Web.Models2;

public partial class Account
{
    public Account() { }
    public Account(string userName, string? password)
    {
        UserName = userName;
        Password = password;
    }
    
    public string UserName { get; set; } = null!;

    public string? Password { get; set; }
}
