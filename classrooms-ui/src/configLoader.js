
export async function getConfig() {
    let serverUrl;
    console.log('dddd')
    console.log(import.meta.env)
    if (import.meta.env.MODE === 'production') {
        const config = await import('../config.prod.js');
        serverUrl = config.serverUrl;
    } else {
        const config = await import('../config.dev.js');
        serverUrl = config.serverUrl;
    }

    return {
        "template" : `${serverUrl}/template`,
        "upload": `${serverUrl}/classrooms`
    };
}
