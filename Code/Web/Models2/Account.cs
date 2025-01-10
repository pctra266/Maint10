using System;
using System.Collections.Generic;

namespace Web.Models2;

public partial class Account
{
    public string UserName { get; set; } = null!;

    public string? Password { get; set; }
}
