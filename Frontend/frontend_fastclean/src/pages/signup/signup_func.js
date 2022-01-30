import React, { Component } from "react";
import "./signupStyle.css";
import { FaMapMarkerAlt, FaPlusCircle } from "react-icons/fa";
import ImgFundo from "../../images/img2.jpg";
import apiCreate from "../../services/api";
import axios, { Axios } from "axios";

class SignupFunc extends Component {
  state = {
    Nome: "",
    Password: "",
    Password2: "",
    Preco:"",
    TipoLimpeza:"",
    Email: "",
    Contacto: "",
    Numero: "",
    CodPostal: "",
    Freguesia: "",
    Rua: "",
    Concelho: "",
    Distrito: "",
    CcFile: "",
    Imagem: "",
    Cadastro: "",
    CartaDeConducao: "",
    HistoricoMedico: "",
    CvFile: "",
    Idade: "",
    errorpass: ""
  };

  SignupFunc = async (exemplo) => {
    exemplo.preventDefault();
    const {
      Nome,
      Password,
      Password2,
      Email,
      Contacto,
      Numero,
      CodPostal,
      Freguesia,
      Preco,
      TipoLimpeza,
      Rua,
      Concelho,
      Distrito,
      CcFile,
      Imagem,
      Cadastro,
      Idade,
      CartaDeConducao,
      HistoricoMedico,
      CvFile,
    } = this.state;

    const form = new FormData();

    form.append("Imagem", this.state.Imagem);
    form.append("CcFile", this.state.CcFile);
    form.append("Nome", this.state.Nome);
    form.append("Password", this.state.Password);
    form.append("Email", this.state.Email);
    form.append("Contacto", this.state.Contacto);
    form.append("Morada.Numero", this.state.Numero);
    form.append("Idade", this.state.Idade);
    form.append("Morada.CodigoPostal", this.state.CodPostal);
    form.append("Morada.Freguesia", this.state.Freguesia);
    form.append("Morada.Rua", this.state.Rua);
    form.append("Morada.Concelho", this.state.Concelho);
    form.append("Morada.Distrito", this.state.Distrito);
    form.append("Cadastro", this.state.Cadastro);
    form.append("CartaDeConducao", this.state.CartaDeConducao);
    form.append("HistoricoMedico", this.state.HistoricoMedico);
    form.append("CvFile", this.state.CvFile);
    form.append("Preco", this.state.Preco);
    form.append("TipoLimpeza", this.state.TipoLimpeza);
    
      if (Password !== Password2) {
        this.setState({ errorpass: "Password nao coincide!!" });
      } else {
        try {
          await axios.post("https://localhost:7067/api/Funcionarios", form);

          this.props.history.push("/");
        } catch (err) {
          this.setState({
            error: "Erro no Registar. Verique os dados preenchidos!",
          });
        }
      }
    
  };

  render() {
    return (
      <div className="displayInfo">
        <image className="Img" src={ImgFundo} />
        <form className="form3" onSubmit={this.SignupFunc}>
          <h2>Registar Funcionário</h2>
          <br />
          <hr />
          <br />
          <div className="input-group3">
            <fieldset className="fsSF">
              {" "}
              <legend>
                {" "}
                <b> Identidade Pessoal</b>{" "}
              </legend>
              {/*<button id="foto">
                <FaPlusCircle />
              </button>
              <button>Imagem Perfil</button>
              <button>Cartão Cidadão</button>
              <button>Cadastro</button>*/}
               <label htmlFor="Imagem">
              <b>Imagem:</b>
            </label>
              <input
                type="file"
                name="Imagem"
                onChange={(exemplo) =>
                  this.setState({ Imagem: exemplo.target.files[0] })
                }
                required
              />
               <label htmlFor="CcFile">
              <b>Cartão de Cidadão:</b>
            </label>
              <input
                type="file"
                name="CcFile"
                onChange={(exemplo) =>
                  this.setState({ CcFile: exemplo.target.files[0] })
                }
                required
              />
               <label htmlFor="Cadastro">
              <b>Cadastro:</b>
            </label>
              <input
                type="file"
                name="Cadastro"
                onChange={(exemplo) =>
                  this.setState({ Cadastro: exemplo.target.files[0] })
                }
                required
              />
              <label htmlFor="CartaDeConducao">
              <b>Carta De Conducao:</b>
            </label>
              <input
                type="file"
                name="CartaDeConducao"
                onChange={(exemplo) =>
                  this.setState({ CartaDeConducao: exemplo.target.files[0] })
                }
                required
              />
              <label htmlFor="HistoricoMedico">
              <b>Historico Medico:</b>
            </label>
              <input
                type="file"
                name="HistoricoMedico"
                onChange={(exemplo) =>
                  this.setState({ HistoricoMedico: exemplo.target.files[0] })
                }
                required
              />
              <label htmlFor="CvFile">
              <b>Curriculum Vitae:</b>
            </label>
              <input
                type="file"
                name="CvFile"
                onChange={(exemplo) =>
                  this.setState({ CvFile: exemplo.target.files[0] })
                }
                required
              />
            </fieldset>
          </div>
          <div className="input-group3">
            <label htmlFor="nome">
              <b>Nome:</b>
            </label>
            <input
              type="text"
              name="Nome"
              onChange={(exemplo) =>
                this.setState({ Nome: exemplo.target.value })
              }
              required
            />
          </div>
          <div className="input-group3">
            <label htmlFor="bdate">
              <b>Data de Nascimento:</b>
            </label>
            <input
              type="date"
              name="Idade"
              onChange={(exemplo) =>
                this.setState({ Idade: exemplo.target.value })
              }
              required
            />
          </div>
          <div className="input-group3">
            <label htmlFor="email">
              <b>Email:</b>
            </label>
            <input
              type="email"
              name="Email"
              onChange={(exemplo) =>
                this.setState({ Email: exemplo.target.value })
              }
              required
            />
          </div>
          <div className="input-group3">
            <label htmlFor="contacto">
              <b>Telemóvel:</b>
            </label>
            <input
              type="number"
              name="contacto"
              pattern="/9[1236][0-9]{7}|2[1-9][0-9]{7}/"
              onChange={(exemplo) =>
                this.setState({ Contacto: exemplo.target.value })
              }
              required
            />
          </div>
          <div className="input-group3">
            <label htmlFor="preco">
              <b>Preço:</b>
            </label>
            <input
              type="number"
              name="Preco"
              onChange={(exemplo) =>
                this.setState({ Preco: exemplo.target.value })
              }
              required
            />
            
          </div>
          <div className="input-group3">
            <label htmlFor="tipoLimpeza">
              <b>Tipo Limpeza:</b>
            </label>
            <input
              type="text"
              name="TipoLimpeza"
              onChange={(exemplo) =>
                this.setState({ TipoLimpeza: exemplo.target.value })
              }
              required
            />
          </div>
          <div className="input-group3">
            <label htmlFor="password">
              <b>Password:</b>
            </label>
            <input
              type="password"
              name="Password"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
              title="Deve conter pelo menos um número e uma letra maiúscula e minúscula, e pelo menos 8 ou mais caracteres"
              required
              onChange={(exemplo) =>
                this.setState({ Password: exemplo.target.value })
              }
            />
          </div>
          <div className="input-group3">
            <label htmlFor="pass2">
              <b>Confirmar Password:</b>
            </label>
            <input
              type="password"
              name="Password2"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
              title="Deve conter pelo menos um número e uma letra maiúscula e minúscula, e pelo menos 8 ou mais caracteres"
              required
              onChange={(exemplo) =>
                this.setState({ Password2: exemplo.target.value })
              }
            />
            {this.state.errorpass && <p>{this.state.errorpass}</p>}
            <br />
          </div>
          <div className="input-group3">
            <fieldset className="fsSF">
              <legend>
                {" "}
                <button id="local">
                  {" "}
                  <FaMapMarkerAlt />
                </button>{" "}
              </legend>
              <br />
              <label htmlFor="distrito">
                <b>Distrito:</b>
              </label>
              <input
                type="text"
                name="Distrito"
                onChange={(exemplo) =>
                  this.setState({ Distrito: exemplo.target.value })
                }
              required

              />
              <label htmlFor="concelho">
                <b>Concelho:</b>
              </label>
              <input
                type="text"
                name="Concelho"
                onChange={(exemplo) =>
                  this.setState({ Concelho: exemplo.target.value })
                }
              required

              />
              <label htmlFor="freguesia">
                <b>Freguesia:</b>
              </label>
              <input
                type="text"
                name="Freguesia"
                onChange={(exemplo) =>
                  this.setState({ Freguesia: exemplo.target.value })
                }
              required

              />
              <label htmlFor="codPostal">
                <b>Código Postal:</b>
              </label>
              <input
                type="text"
                name="CodPostal"
                pattern="\d{4}([\-]\d{3})?"
                title="Tem que ser no formato 0000-000"
                onChange={(exemplo) =>
                  this.setState({ CodPostal: exemplo.target.value })
                }
              required

              />
              <label htmlFor="rua">
                <b>Rua:</b>
              </label>
              <input
                type="text"
                name="Rua"
                onChange={(exemplo) =>
                  this.setState({ Rua: exemplo.target.value })
                }
              required

              />
              <label htmlFor="numero">
                <b>Número:</b>
              </label>
              <input
                type="text"
                name="Numero"
                onChange={(exemplo) =>
                  this.setState({ Numero: exemplo.target.value })
                }
              required

              />
            </fieldset>
          </div>
          <div>
             
          {this.state.error && <p>{this.state.error}</p>}
          </div>
         
          <br />
          <button className="primary">Registar</button>
        </form>
      </div>
    );
  }
}

export default SignupFunc;
