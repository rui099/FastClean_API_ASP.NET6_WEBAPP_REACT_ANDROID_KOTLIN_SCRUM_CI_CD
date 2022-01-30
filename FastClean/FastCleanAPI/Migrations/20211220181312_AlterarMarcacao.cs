using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class AlterarMarcacao : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador");

            migrationBuilder.AlterColumn<int>(
                name: "FuncionarioId",
                table: "Marcacoes",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<string>(
                name: "DiaHora",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(DateTime),
                oldType: "datetime2");

            migrationBuilder.AlterColumn<string>(
                name: "Detalhes",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<int>(
                name: "ClienteId",
                table: "Marcacoes",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddColumn<string>(
                name: "DuracaoTotal",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "HoraFinal",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "HoraInicial",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<double>(
                name: "Total",
                table: "Marcacoes",
                type: "float",
                nullable: false,
                defaultValue: 0.0);

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens",
                column: "ChatId",
                principalTable: "Chats",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens",
                column: "SenderId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador",
                column: "MoradaId",
                principalTable: "Moradas",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador");

            migrationBuilder.DropColumn(
                name: "DuracaoTotal",
                table: "Marcacoes");

            migrationBuilder.DropColumn(
                name: "HoraFinal",
                table: "Marcacoes");

            migrationBuilder.DropColumn(
                name: "HoraInicial",
                table: "Marcacoes");

            migrationBuilder.DropColumn(
                name: "Total",
                table: "Marcacoes");

            migrationBuilder.AlterColumn<int>(
                name: "FuncionarioId",
                table: "Marcacoes",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "DiaHora",
                table: "Marcacoes",
                type: "datetime2",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified),
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Detalhes",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "ClienteId",
                table: "Marcacoes",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens",
                column: "ChatId",
                principalTable: "Chats",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens",
                column: "SenderId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador",
                column: "MoradaId",
                principalTable: "Moradas",
                principalColumn: "Id");
        }
    }
}
