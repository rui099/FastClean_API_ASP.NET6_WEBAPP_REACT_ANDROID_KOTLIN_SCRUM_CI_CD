import React from "react";
import "./marcacoesDropDown.css";
import {
  Nav,
  NavLink,
  Bars,
  NavMenu,
  NavBtn,
  NavBtnLink,
  NavIcon,
} from "./NavbarElements";

import { logout, isAuthenticated, getCargo } from "../../services/auth";

import { FaArrowDown } from "react-icons/fa";

const Navbar = () => {
  return (
    <div class="navBar">
      <Nav>
        <NavIcon to="/">
          <img
            src={require("../../images/logo.png").default}
            width="150"
            height="100"
          />
        </NavIcon>
        <Bars />
        <NavMenu>
          {isAuthenticated() && getCargo() !== "Administrador" && (
            <div class="dropdown">
              <NavBtn to="">
                Marcações &nbsp; <FaArrowDown />
              </NavBtn>
              <div class="dropdown-content">
                <a class="listaMarcacao" href="/marcacoesAindaNaoAceites">
                  Não Aceites
                </a>
                <a class="listaMarcacao" href="/marcacoesTerminadas">
                  Terminadas
                </a>
                <a class="listaMarcacao" href="/marcacoesADecorrer">
                  A Decorrer
                </a>
              </div>
            </div>
          )}

          {isAuthenticated() && getCargo() === "Administrador" && (
            <div class="dropdown">
              <NavBtn to="">
                Reports &nbsp; <FaArrowDown />
              </NavBtn>
              <div class="dropdown-content">
                <a class="listaMarcacao" href="/reports">
                  Listagem
                </a>
                <a class="listaMarcacao" href="/usersBanidos">
                  Users Banidos
                </a>
              </div>
            </div>
          )}

          {isAuthenticated() && getCargo() === "Administrador" && (
            <div class="dropdown">
              <NavBtn to="">
                Candidaturas &nbsp; <FaArrowDown />
              </NavBtn>
              <div class="dropdown-content">
                <a class="listaMarcacao" href="/candidatos">
                  Funcionários
                </a>
                <a class="listaMarcacao" href="/candidatosClientes">
                  Clientes
                </a>
                <a class="listaMarcacao" href="/usersAceites">
                  Aceites (Funcionários)
                </a>
                <a class="listaMarcacao" href="/usersAceitesClientes">
                  Aceites (Clientes)
                </a>
              </div>
            </div>
          )}

          {isAuthenticated() && getCargo() === "Administrador" && (
            <div class="dropdown">
              <NavBtn to="">
                Marcações &nbsp; <FaArrowDown />
              </NavBtn>
              <div class="dropdown-content">
                <a class="listaMarcacao" href="/verMarcacoesNaoAceitesAdmin">
                  Não Aceites
                </a>
                <a class="listaMarcacao" href="/verMarcacoesTerminadasAdmin">
                  Terminadas
                </a>
                <a class="listaMarcacao" href="/verMarcacoesADecorrerAdmin">
                  A Decorrer
                </a>
              </div>
            </div>
          )}

          {isAuthenticated() && getCargo() !== "Administrador" && (
            <NavLink to="/perfil" activeStyle>
              Perfil
            </NavLink>
          )}

          {!isAuthenticated() && (
            <NavLink to="/signupCli" activeStyle>
              {" "}
              Registar Cliente{" "}
            </NavLink>
          )}
          {!isAuthenticated() && (
            <NavLink to="/signupFunc" activeStyle>
              {" "}
              Registar Funcionário{" "}
            </NavLink>
          )}

          {isAuthenticated() && (
            <NavBtn>
              <NavBtnLink onClick={() => logout()} to="">
                Logout
              </NavBtnLink>
            </NavBtn>
          )}
        </NavMenu>
      </Nav>
    </div>
  );
};

function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function (event) {
  if (!event.target.matches(".dropbtn")) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains("show")) {
        openDropdown.classList.remove("show");
      }
    }
  }
};

export default Navbar;
