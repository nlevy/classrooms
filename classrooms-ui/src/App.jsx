import DownloadTemplate from "./components/DownloadTemplate.jsx"
import UploadFile from "./components/UploadFile.jsx"

function App() {
    const TEMPLATE_URL = 'http://127.0.0.1:8080/template';// TODO move to central external config
    const UPLOAD_URL = 'http://127.0.0.1:8080/classrooms';// TODO move to central external config
  return (
      <>
          <div id="title">
              <h1>Classrooms Builder</h1>
          </div>
          <div id="center">
              <div id="main">
                  <UploadFile uploadUrl={UPLOAD_URL}/>
                  <DownloadTemplate apiUrl={TEMPLATE_URL}/></div>
          </div>
      </>
  )
}

export default App
