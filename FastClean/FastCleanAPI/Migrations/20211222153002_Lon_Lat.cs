using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class Lon_Lat : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Latitude",
                table: "Utilizador",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Longitude",
                table: "Utilizador",
                type: "nvarchar(max)",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Latitude",
                table: "Utilizador");

            migrationBuilder.DropColumn(
                name: "Longitude",
                table: "Utilizador");
        }
    }
}
