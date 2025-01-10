using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace Web.Models2;

public partial class MaintainManagementContext : DbContext
{
    public MaintainManagementContext()
    {
    }

    public MaintainManagementContext(DbContextOptions<MaintainManagementContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Account> Accounts { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder){
        var config = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build();
        optionsBuilder.UseSqlServer(config.GetConnectionString("DefaultConnection"));
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Account>(entity =>
        {
            entity.HasKey(e => e.UserName).HasName("PK__Account__C9F2845775755407");

            entity.ToTable("Account");

            entity.Property(e => e.UserName).HasMaxLength(50);
            entity.Property(e => e.Password).HasMaxLength(6);
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
