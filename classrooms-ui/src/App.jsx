import './App.css'
import DownloadTemplate from "./components/DownloadTemplate.jsx"
import UploadFile from "./components/UploadFile.jsx"

function App() {
    const TEMPLATE_URL = 'http://127.0.0.1:8080/template';// TODO move to central external config
    const UPLOAD_URL = 'http://127.0.0.1:8080/classrooms';// TODO move to central external config
  return (
    <>
      <DownloadTemplate apiUrl={TEMPLATE_URL}/>
      <UploadFile uploadUrl={UPLOAD_URL}/>
    </>
  )
}

export default App
