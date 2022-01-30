import React from 'react';
import './Styles/style.css';
import Navbar from './Components/Navbar';
import Footer from './Components/Footer/footer';
import { BrowserRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import marcacoesADecorrer from './pages/marcacoes/marcacoesADecorrer';
import Reports from './pages/reports/reports';
import Perfil from "./pages/perfil/perfil";
import VerReport from './pages/reports/verReports';
import EditPass from './pages/edit_password/editpass';
import HomePage from './pages/homepage/home';
import SignupCli from './pages/signup/signup_client';
import SinupFunc from './pages/signup/signup_func';
import Login from './pages/login/login';
import Owners from './pages/owners/owners';
import Candidatos from './pages/candidaturas_func/candidatos';
import VerCandidatos from './pages/candidaturas_func/verCandidatos';
import { isAuthenticated , getCargo } from "./services/auth";
import ForgotPass from './pages/forgot_pass/forgot_pass';
import CandidatosAceites from './pages/candidatosAceites/usersAceites';
import CandidatosAceitesClientes from './pages/candidatosAceitesClientes/candidatosAceitesClientes';
import MarcacoesNaoAceites from './pages/marcacoes/marcacoesAindaNaoAceites';
import MarcacoesTerminadas from './pages/marcacoes/marcacoesTerminadas';
import MarcacoesDetails from './pages/marcacoes/marcacoesDetails';
import usersBanidos from './pages/usersBanidos/usersBanidos';
import verUserBanido from './pages/usersBanidos/verUserBanido';
import CandidatosClientes from './pages/candidatos_clientes/candidatosClientes';
import verCandidatosClientes from './pages/candidatos_clientes/verCandidatosClientes';
import marcacoesADecorrerAdmin from './pages/MarcacacoesAdmin/marcacoesADecorrerAdmin';
import marcacoesTerminadasAdmin from './pages/MarcacacoesAdmin/marcacoesTerminadasAdmin';
import marcacoesNaoAceitesAdmin from './pages/MarcacacoesAdmin/marcacoesNaoAceitesAdmin';
import changePersonalDataFunc from './pages/changePersonalDataFunc/changePersonalDataFunc';
import changePersonalDataCli from './pages/changePersonalDataCli/changePersonalDataCli';


function App() {
	
  const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route
      {...rest}
      render={props =>
        isAuthenticated() ? (
          <Component {...props} />
        ) : (
          <Redirect to={{ pathname: "/homePage", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/editPass", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/marcacoesADecorrer", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/reports", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/perfil", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/changePersonalDataFunc", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/changePersonalDataCli", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/reports", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verReports", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/candidatos", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verCandidatos", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/usersAceites", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/usersAceitesClientes", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/marcacoesAindaNaoAceites", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/marcacoesTerminadas", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/marcacaoDetails", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/usersBanidos", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verUserBanido", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/candidatosClientes", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verMarcacoesADecorrerAdmin", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verMarcacoesTerminadasAdmin", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verMarcacoesNaoAceitesAdmin", state: { from: props.location } }} />,
          <Redirect to={{ pathname: "/verCandidatosClientes", state: { from: props.location } }} />
          
        )
      }
    />
  );

  return (
    <>
      <Router>
        
      <Navbar /> 
          <div class="background">
          <Switch>
        
            {isAuthenticated() && <PrivateRoute path="/homePage" component={HomePage} /> }
            {!isAuthenticated() &&  <Route path="/" exact component={Login} /> }
            {isAuthenticated() &&  <Route path="/" exact component={HomePage} /> }
 	          {!isAuthenticated() && <Route path="/forgotPass" component={ForgotPass} /> }
            {!isAuthenticated() && <Route path="/owners" exact component={Owners} />}
            {isAuthenticated() && (getCargo() !== "Administrador") && <PrivateRoute path="/marcacoesADecorrer" component={marcacoesADecorrer} />}
            {isAuthenticated() && (getCargo() !== "Administrador")  && <PrivateRoute path="/marcacoesAindaNaoAceites" component={MarcacoesNaoAceites} /> }
            {isAuthenticated() && (getCargo() !== "Administrador")  && <PrivateRoute path="/marcacoesTerminadas" component={MarcacoesTerminadas} /> }
            {isAuthenticated() && <PrivateRoute path="/marcacaoDetails" component={MarcacoesDetails} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/reports" component={Reports} /> }
            {isAuthenticated() && (getCargo() !== "Administrador")  && <PrivateRoute path="/perfil" component={Perfil} /> }
            {isAuthenticated() && (getCargo() === "Funcionario") && <PrivateRoute path="/changePersonalDataFunc" component={changePersonalDataFunc} /> }
            {isAuthenticated() && (getCargo() === "Cliente") && <PrivateRoute path="/changePersonalDataCli" component={changePersonalDataCli} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verReports" component={VerReport} /> }
            {isAuthenticated() && <PrivateRoute path="/editPass" component={EditPass} /> }
            {!isAuthenticated() && <Route path="/signupCli" component={SignupCli} />  } 
            {!isAuthenticated() && <Route path="/signupFunc" component={SinupFunc} />   } 
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/candidatos"  component={Candidatos} /> } 
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verCandidatos"  component={VerCandidatos} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/candidatosClientes"  component={CandidatosClientes} /> } 
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verCandidatosClientes"  component={verCandidatosClientes} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/usersAceites"  component={CandidatosAceites} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/usersAceitesClientes"  component={CandidatosAceitesClientes} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/usersBanidos"  component={usersBanidos} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verMarcacoesADecorrerAdmin"  component={marcacoesADecorrerAdmin} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verMarcacoesTerminadasAdmin"  component={marcacoesTerminadasAdmin} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verMarcacoesNaoAceitesAdmin"  component={marcacoesNaoAceitesAdmin} /> }
            {isAuthenticated() && (getCargo() === "Administrador") && <PrivateRoute path="/verUserBanido"  component={verUserBanido} /> }

          </Switch>
          </div>
          <Footer />
        </Router>
        </>  
  );
}

export default App;