export class VideoDocument {
    constructor(
        public id?: number,
        public documentName?: string,
        public sourceFilePath?: string,
        public documentFilePath?: string,
        public videoTypeId?: number,
    ) {
    }
}
