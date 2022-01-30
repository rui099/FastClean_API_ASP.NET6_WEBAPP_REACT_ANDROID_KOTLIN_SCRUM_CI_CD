import React, {Component} from 'react';
import Table from '@mui/material/Table';
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { maxWidth } from "@material-ui/system";
import {NavIcon } from './reportsElements';
import axios from "axios";
import { getCargo } from "../../services/auth";

function refresh() {
  window.setTimeout(()=>{
      window.location.reload();
  }, 5);
}

class Reports extends Component {

  state = {
    reports: []
  };
  
  async componentDidMount() {
    
  if (getCargo() === "Administrador") {
      const response = await axios.get(
        "https://localhost:7067/api/Reports/"
      );
      this.setState({ reports: response.data });
  }

  }

  render() {
    
    const { reports } = this.state;
   
    return(
     <div class="displayInfo" >
        
        <h1>Lista de Reports</h1>
       
        <TableContainer component={Paper} style={{borderRadius: 50}}>
          <Table 
            sx={{ minWidth: 650, maxWidth: 2000 }}
            size="small"
            aria-label="a dense table"
            
          >
            <TableHead> 
              <TableRow>
                <TableCell style={{textAlign:'center'}}> <h3> Reporter </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Reported </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Título </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Tipo </h3></TableCell>
                <TableCell style={{textAlign:'center'}}> <h3> Ação </h3> </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {reports.map((report) => ( 
               
              (report.arquivado===false && report.reported.banido===false) ? <TableRow
                  key={reports.reporter}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                 <TableCell style={{textAlign:'center', fontSize:16}}> {report.reporter.nome} ({report.reporter.cargo === 0 ? "Funcionário" : "Cliente"}) </TableCell>
                 <TableCell style={{textAlign:'center', fontSize:16}}> {report.reported.nome} ({report.reported.cargo === 0 ? "Funcionário" : "Cliente"}) </TableCell>
                 <TableCell style={{textAlign:'center', fontSize:16}}> {report.titulo} </TableCell>
                 <TableCell style={{textAlign:'center', fontSize:16}}> {report.tipo === 0 ? "Chat" : "Serviço"} </TableCell>
                  <TableCell style={{textAlign:'center', fontSize:16}}> 
                    <NavIcon to= {"/verReports/" + (report.reporter.cargo === 0 ? "Funcionario" : "Cliente") + "/" + report.id + "/" + report.reported.id} onClick={refresh} >
                        
                        <img  
                      src={require("../../images/eye.png").default}
                      width="60"
                      height="40" 
                    />
                  
                    </NavIcon>      
                  </TableCell>    
                </TableRow> : null
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </div> 
    ); 
}
}

export default Reports;